using Abp.Application.Services;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.UI;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.MoveModelMenu.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;

namespace XMX.WMS.MoveModelMenu
{
    public class MoveModelMenuService : AsyncCrudAppService<MoveModelMenu, MoveModelMenuDto, Guid, MoveModelMenuPagedRequest, MoveModelMenuCreatedDto, MoveModelMenuUpdatedDto>, IMoveModelMenuService
    {
        public MoveModelMenuService(IRepository<MoveModelMenu, Guid> repository) : base(repository)
        {
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<MoveModelMenu> CreateFilteredQuery(MoveModelMenuPagedRequest input)
        {
            return Repository.GetAll().Where(x => x.menu_type == MenuType.菜单)
                .WhereIf(!input.function_name.IsNullOrWhiteSpace(), x => x.menu_function_name.Contains(input.function_name))
                .WhereIf(!input.menu_url.IsNullOrWhiteSpace(), x => x.menu_url.Contains(input.menu_url));
        }

        /// <summary>
        /// 获取树形系统模块数据
        /// </summary>
        /// <param name="input">查询参数</param>
        /// <returns>树形系统模块数据</returns>
        public List<MoveModelMenuTreeList> GetMoveModelMenuTree(MoveModelMenuPagedRequest input)
        {
            //父级
            var olist = Repository.GetAll().Select(item => new { item.Id, item.menu_function_name, item.menu_parent_id }).ToList();
            var plist = olist.FindAll(x => x.menu_parent_id == null);
            //子级
            var list = olist.FindAll(x => x.menu_parent_id != null);
            var relist = new List<MoveModelMenuTreeList>();
            foreach (var item in plist)
            {
                var arr = list.FindAll(x => x.menu_parent_id == item.Id).ToList();
                List<MoveModelMenuNode> output = new List<MoveModelMenuNode>();
                foreach (var i in arr)
                    output.Add(new MoveModelMenuNode { id = i.Id, label = i.menu_function_name });
                relist.Add(new MoveModelMenuTreeList { id = item.Id, label = item.menu_function_name, children = output });
            }
            return relist;
        }

        /// <summary>
        /// 父级下拉列表
        /// </summary>
        /// <returns>父级下拉列表数据</returns>
        public List<MoveModelMenuDto> GetMoveModelMenuSelectedList()
        {
            //二级目录树，menu_parent_id字段为空则为父级节点
            var list = Repository.GetAll().Select(item => new
            {
                item.Id,
                item.menu_function_name,
                item.menu_parent_id
            }).Where(x => x.menu_parent_id == null).ToList();
            var relist = new List<MoveModelMenuDto>();
            foreach (var item in list)
                relist.Add(new MoveModelMenuDto
                {
                    Id = item.Id,
                    menu_function_name = item.menu_function_name
                });
            return relist;
        }
        /// <summary>
        /// 获取下级模块列表
        /// </summary>
        /// <param name="subMenuRequest"></param>
        /// <returns></returns>
        public async Task<List<MoveModelMenu>> GetMoveSubMenuInfoList(SubMenuRequest subMenuRequest)
        {
            return await Repository.GetAllListAsync(x => x.menu_parent_id == subMenuRequest.parentId && x.menu_type == subMenuRequest.menu_type);
        }

        public List<object> GetPermissionList()
        {
            //先查主目录
            var list = Repository.GetAll().Select(item => new
            {
                item.Id,
                item.menu_function_name,
                item.menu_parent_id,
                item.menu_icon,
                item.menu_type,
                item.menu_is_enable,
                item.menu_remark
            }).Where(x => x.menu_parent_id == null && x.menu_is_enable.Equals(WMSIsEnabled.启用)).ToList();
            List<object> output_list = new List<object>();
            int master_index = 100;
            //利用变量step来避免sortID可能出现重复值
            int step = 1;
            foreach (var item in list)
            {
                //查询链接列表
                var sublist = Repository.GetAll().Where(x => x.menu_parent_id.Equals(item.Id) && x.menu_type.Equals(MenuType.链接)).ToList();
                List<object> linklist = new List<object>();
                int link_index = master_index * 10*step;
                //利用变量count来避免sortID可能出现重复值
                int count = 1;
                foreach (var index in sublist)
                {    
                    //查询按钮列表
                    var btnlist = Repository.GetAll().Where(x => x.menu_parent_id.Equals(index.Id) && x.menu_type.Equals(MenuType.按钮)).ToList();
                    List<object> btlist = new List<object>();
                    int btn_index = link_index * 10*count;
                    if (btnlist.Count > 0)
                    {
                        foreach (var btnindex in btnlist)
                            btlist.Add(new { 
                                sortId = btn_index++, 
                                id = btnindex.Id, 
                                label = btnindex.menu_function_name, 
                                permission = btnindex.menu_remark, 
                                master = index.menu_function_name,
                                order = btnindex.menu_order,
                                menuType = btnindex.menu_type
                            });
                    }
                    if (btlist.Count > 0)
                        linklist.Add(new { 
                            sortId = link_index++, 
                            id = index.Id, 
                            label = index.menu_function_name, 
                            permission = index.menu_remark, 
                            link = index.menu_url, 
                            master = item.menu_function_name, 
                            children = btlist,
                            menuType = index.menu_type
                        });
                    else
                        linklist.Add(new { 
                            sortId = link_index++, 
                            id = index.Id, 
                            label = index.menu_function_name, 
                            permission = index.menu_remark, 
                            link = index.menu_url, 
                            master = item.menu_function_name,
                            menuType = index.menu_type
                        });
                    count++;
                }
                if (linklist.Count > 0)
                    output_list.Add(new { 
                        sortId = master_index++, 
                        id = item.Id, 
                        label = item.menu_function_name, 
                        icon = item.menu_icon,
                        permission = item.menu_remark,
                        children = linklist,
                        menuType = item.menu_type,
                        disabled = false
                    });
                else
                    output_list.Add(new {
                        sortId = master_index++, 
                        id = item.Id, 
                        label = item.menu_function_name, 
                        icon = item.menu_icon,
                        permission = item.menu_remark,
                        menuType = item.menu_type,
                        disabled = false
                    });
                step++;
            }
            return output_list;
        }
    }
}
