using Abp.Application.Services;
using Abp.Collections.Extensions;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.UI;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.Storage;
using Microsoft.Extensions.Configuration;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using XMX.WMS.ReportTemp.Dto;
using XMX.WMS.Configuration;
using Abp.Application.Services.Dto;

namespace XMX.WMS.ReportTemp
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-18 14:58:30
    /// 描 述：
    ///</summary>
    public class ReportTempService : AsyncCrudAppService<ReportTemp, ReportTempDto, Guid, ReportTempPagedRequest, ReportTempCreatedDto, ReportTempUpdatedDto>, IReportTempService
    {
        private readonly IConfigurationRoot _appConfiguration;
        public ReportTempService(IRepository<ReportTemp, Guid> repository, IHostingEnvironment env) : base(repository)
        {
            _appConfiguration = AppConfigurations.Get(env.ContentRootPath, env.EnvironmentName, env.IsDevelopment());
        }
        /// <summary>
        /// 通过报表id，返回报表对应的查询内容
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public object GetReportData(EntityDto<Guid> input)
        {
            ReportTemp reportTemp = Repository.Get(input.Id);
            if (reportTemp == null)
                throw new UserFriendlyException("报表不存在");
            if (reportTemp.ParamJson.IsNullOrWhiteSpace())
                throw new UserFriendlyException("自定义sql语句不能为空");
            try
            {   string type = reportTemp.TempStyle;
                DataTable dt = GetSqlQueryForDataTatable(reportTemp.ParamJson);
                return new
                {
                    tempStyle = reportTemp.TempStyle,
                    chartType = reportTemp.TempType,
                    listData = dt
                };
            }
            catch(Exception e)
            {
                throw new UserFriendlyException("查询语句错误");
            }       
        }

        /// <summary>
        /// EF SQL 语句返回 dataTable
        /// </summary>
        /// <param name="db"></param>
        /// <param name="sql"></param>
        /// <param name="parameters"></param>
        /// <returns></returns>
        private DataTable GetSqlQueryForDataTatable(string sql)
        {
            SqlConnection conn = new SqlConnection(_appConfiguration["ConnectionStrings:SQLConn"]);
            SqlCommand cmd = new SqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = sql;

            SqlDataAdapter adapter = new SqlDataAdapter(cmd);
            DataTable table = new DataTable();
            adapter.Fill(table);

            conn.Close();//连接需要关闭
            conn.Dispose();
            return table;
        }
    }

}

