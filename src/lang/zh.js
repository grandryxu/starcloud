const plInput= '请输入';
const plSelect = '请选择';
export default {
  lang: {
    zh: '中文',
    en:'English',
    ja:'日語'
  },
  route: {
    dashboard: '首页',
    documentation: '文档',

    BasicInfoManage: '基础信息管理',
    SystemManageCenter: '系统管理中心',
    ImportWarehouseManage: '入库管理中心',
    ExportWarehouseManage:'出库管理中心',
    TaskManage: '任务管理中心',
    BusinessManage: '业务管理中心',
    InventoryManage: '库存管理中心',
    LogManage: '日志管理中心',
    WarehouseWarning: '库存预警中心',
    WarehouseReport: '仓库报表中心',
    WareEquipment: '仓库设备中心',
    QualityManagement:'质量管理中心',
    
    //基础信息管理
    MaterialBasisInfo: '物料基础信息',
    MaterialMeasureUnit: '物料计量单位',
    MaterialQualityStatus: '物料质量状态',
    CustomerCategoryInfo: '客户类别信息',
    CustomerBaseInfo: '客户基础信息',
    WarehouseBaseInfo: '仓库基础信息',
    AreaBasicInfo: '库区基础信息',
    SlotBasicInfo: '库位基础信息',
    BillTypeInfo: '单据类型信息',
    InOutdBasicInfo: '出入口基础信息',
    PlatFormBasicInfo: '月台基础信息',
    packBasicInfo: '垛形基础信息',
    
    //系统管理中心
    CompanyBasicInfo: '公司基础信息',
    DepartmentBasicInfo: '部门基础信息',
    StaffBasicInfo: '人员基础信息',
    RoleBasicInfo: '角色基础信息',
    SystemModuleManage: '系统模块管理',
    MoveModuleManage: '移动模块管理',
    WarehouseBasicManage: '仓库基础管理',
    SlotInit: '库位初始化',
    
    //入库管理中心
    ImportBillManage: '入库单据管理',
    ImportBillBodyManage: '入库流水管理',
    EmptyStockImport: '空托盘入库流水',

    //出库管理中心
    ExportBillManage: '出库单据管理',
    ExportBillBodyManage: '出库流水管理',
    EmptyStockExport: '空托盘出库流水',
    
    //任务管理中心
    MainTaskManage: '立库任务管理',
    MainHistorytask: '立库历史任务',
    FlatBankTask:'平库任务管理',
    PickingTask:'拣选任务管理',
    AGVTask:'AGV任务管理',
    RGVTask:'RGV任务管理',

    
    //业务管理中心
    StrategyMonitorManage: '监控策略管理',
    StrategyPutawayManage: '上架策略管理',
    StrategyDistriManage: '分配策略管理',
    EncodingRuleManage: '编码规则管理',
    
    //库存管理中心
    InventoryMaterialManage: '物料总库存管理',
    InventoryStockingManage: '盘点管理',
    VisualizationManage: '可视化库存管理',
    TotalInventoryManage: '总库存管理',
    SlotInventoryManage: '库位库存管理',
    StockInventoryManage: '托盘库存管理',
    
    //日志管理中心
    LoginLogManage: '登录日志管理',
    OptLogManage: '操作日志管理',
    MachineAlertLog: '设备报警日志',
    ImportApplyLog:'入库申请日志',
    ExportBillSyncLog:'出库单据同步日志',
    
    //库存预警管理
    DullStockWarning: '呆滞库存预警',
    ValidityStockWarning: '效期库存预警',
    InventoryThresholdWarning:'库存阈值预警',
    ImportExceptionWarning:'入库异常预警',
    ExportExceptionWarning:'出库异常预警',
    InventoryExceptionWarning:'库存异常预警',
    
    //仓库报表中心
    ExportImportStandingBook: '出入库台账',
    StorageUseRatio: '货位利用率',
    ImportReportForm: '入库报表',
    ExportReportForm: '出库报表',
    inventoryReportForm: '库存报表',
    
    //仓库设备中心
    RGVStateManage: 'RGV状态管理',
    StackerStateManage:'堆垛机状态管理',

    //质量管理中心
    QualityReleased: '质量放行管理',
    QualityCheckout: '质量抽检管理',
    
    
  },
  navbar: {
    changePwd:'修改密码',
    logOut: '退出登录',
    dashboard: '首页',
    github: '项目地址',
    theme: '换肤',
    size: '布局大小',
    profile: '个人中心',
    administrator: '管理员',
    operation:'操作',
    oldpsd:'原密码',
    newpsd:'新密码',
    confirmpsd:'确认密码',
    newpsdnull:'新密码不能为空',
    oldpsdnull:'原密码不能为空',
    confirmpsdnull:'确认密码不能为空',
    psdnotmatch:'两次输入密码不一致',
    changepsdtip:'确定修改密码吗?',
    tiptitle:'提示',
    changesucc:'修改成功',
    exitsys:'您确认退出本系统吗？',
    systembusy:'系统繁忙请稍后再试'
  },
  login: {
    title: '系统登录',
    logIn: '登录',
    username: '输入账号',
    password: '输入密码',
    any: '随便填',
    thirdparty: '第三方登录',
    thirdpartyTips: '本地不能模拟，请结合自己业务进行模拟！！！',
    forgotPassword: '忘记密码',
    register:'注册',
    fail:'登陆失败',
    success:'登陆成功',
    loading:'登陆中',
    error:'用户名或者密码错误',
  },
  import: { 
    stocknull:"请输入托盘号码",
    slotnull:"请输入库位",
    quantitynull:"请输入托盘数量",
    receiptno:"入库单号",
    contractno:"合同号",
    supplier:"供应商",
    pleaseinput:"请输入",
    pleaseselect:"请选择",
    documenttype:"单据类型",
    state:"状态",
    warehouse:"仓库",
    documentdate:"单据日期",
    startdate:"起始日期",
    endtdate:"结束日期",
    search:"查询",
    palletstatus:"托盘状态",
    materialquantity:"物资数量",
    palletbarcode:"托盘条码",

  },
  documentation: {
    documentation: '文档',
    github: 'Github 地址'
  },
  permission: {
    createRole: '新增角色',
    editPermission: '编辑权限',
    roles: '你的权限',
    switchRoles: '切换权限',
    tips: '在某些情况下，不适合使用 v-permission。例如：Element-UI 的 el-tab 或 el-table-column 以及其它动态渲染 dom 的场景。你只能通过手动设置 v-if 来实现。',
    delete: '删除',
    confirm: '确定',
    cancel: '取消',
    empty:'清空',
    print:'打印',
    add:'新增'
  },
  guide: {
    description: '引导页对于一些第一次进入项目的人很有用，你可以简单介绍下项目的功能。本 Demo 是基于',
    button: '打开引导'
  },
  components: {
    documentation: '文档',
    tinymceTips: '富文本是管理后台一个核心的功能，但同时又是一个有很多坑的地方。在选择富文本的过程中我也走了不少的弯路，市面上常见的富文本都基本用过了，最终权衡了一下选择了Tinymce。更详细的富文本比较和介绍见',
    stickyTips: '当页面滚动到预设的位置会吸附在顶部',
    backToTopTips1: '页面滚动到指定位置会在右下角出现返回顶部按钮',
    backToTopTips2: '可自定义按钮的样式、show/hide、出现的高度、返回的位置 如需文字提示，可在外部使用Element的el-tooltip元素',
    imageUploadTips: '由于我在使用时它只有vue@1版本，而且和mockjs不兼容，所以自己改造了一下，如果大家要使用的话，优先还是使用官方版本。'
  },
  table: {
    dynamicTips1: '固定表头, 按照表头顺序排序',
    dynamicTips2: '不固定表头, 按照点击顺序排序',
    dragTips1: '默认顺序',
    dragTips2: '拖拽后顺序',
    title: '标题',
    importance: '重要性',
    type: '类型',
    remark: '点评',
    search: '查询',
    export: '导出',
    reviewer: '审核人',
    // id: '序号',
    date: '日期',
    author: '作者',
    readings: '阅读数',
    status: '状态',
    actions: '操作',
    edit: '编辑',
    publish: '发布',
    draft: '草稿',
    delete: '删除',
    cancel: '取 消',
    confirm: '确 定'
  },
  example: {
    warning: '创建和编辑页面是不能被 keep-alive 缓存的，因为keep-alive 的 include 目前不支持根据路由来缓存，所以目前都是基于 component name 来进行缓存的。如果你想类似的实现缓存效果，可以使用 localStorage 等浏览器缓存方案。或者不要使用 keep-alive 的 include，直接缓存所有页面。详情见'
  },
  errorLog: {
    tips: '请点击右上角bug小图标',
    description: '现在的管理后台基本都是spa的形式了，它增强了用户体验，但同时也会增加页面出问题的可能性，可能一个小小的疏忽就导致整个页面的死锁。好在 Vue 官网提供了一个方法来捕获处理异常，你可以在其中进行错误处理或者异常上报。',
    documentation: '文档介绍'
  },
  // excel: {
  //   export: '导出',
  //   selectedExport: '导出已选择项',
  //   placeholder: '请输入文件名(默认excel-list)'
  // },
  // zip: {
  //   export: '导出',
  //   placeholder: '请输入文件名(默认file)'
  // },
  pdf: {
    tips: '这里使用 window.print() 来实现下载pdf的功能'
  },
  theme: {
    change: '换肤',
    documentation: '换肤文档',
    tips: 'Tips: 它区别于 navbar 上的 theme-pick, 是两种不同的换肤方法，各自有不同的应用场景，具体请参考文档。'
  },
  tagsView: {
    refresh: '刷新',
    close: '关闭',
    closeOthers: '关闭其它',
    closeAll: '关闭所有'
  },
  settings: {
    title: '系统布局配置',
    theme: '主题色',
    showTagsView: '显示 Tags-View',
    showSidebarLogo: '显示侧边栏 Logo',
    fixedHeader: '固定 Header',
    sidebarTextTheme: '侧边栏文字主题色',
    company: '江苏新美星物流科技有限公司',
    WarehousingSystem: '仓库管理系统',
  },
  public:{
    stockcode:'托盘号码',
    warehouse:'仓库',
    select:'请选择',
    slot:'库位',
    quantity:'数量',
    unit:'计量单位',
    plSelectunit:plSelect + '计量单位',
    materialNum:'物料编号',
    pleaseInputMaterialNum:'请输入物料编号',
    materialName:'物料名称',
    pleaseInputMaterialName:'请输入物料名称',
    serialNumber:'序号',
    plInput:plInput,
    plSelect:plSelect,
    saveDate:'保 存',
  },
  //物料基础信息
  materialBase:{
    storageArea:'存放区域',
    plSelectStorageArea:plSelect +'存放区域',
    monitoringStrategy:'监控策略',
    plSelectMonitoringStrategy:plSelect +'监控策略',
    allocationStrategy:'分配策略',
    plSelectAllocationStrategy:plSelect + '分配策略',
    saleStrategy:'上架策略',
    plSelectSaleStrategy:plSelect + '上架策略',
    bigPackageNumber:'大包装数',
    midPackageNumber:'中包装数',
    smallPackageNumber:'小包装数',
    materialType:'物料类型',
    type:'型号',
    abbreviation:'简称',
    englishName:'英文名',
    bigPackageQuantity:'大包装数量',
    midPackageQuantity:'中包装数量',
    smallPackageQuantity:'小包装数量',
    highestWaterLevel:'最高水位',
    lowestWterLevel:'最低水位',
    singlePlateQuantity:'单盘数量',
    unitWeight:'单件重量',
    unitPrice:'单价',
    auxiliaryUnitOfMeasurement:'辅助计量单位',
    PeriodOfValidityDay:'有效期(保质期、天)',
    buttressType:'垛型',
    materialDescription:'物料描述',
    externalSystemID:'外部系统ID',
    materialPicture:'物料图片',

    plSelectmaterialType:plSelect + '物料类型',
    plInputtype:plInput + '型号',
    plInputabbreviation:plInput + '简称',
    plInputenglishName:plInput + '英文名',
    plInputbigPackageQuantity:plInput + '大包装数量',
    plInputmidPackageQuantity:plInput + '中包装数量',
    plInputsmallPackageQuantity:plInput + '小包装数量',
    plInputhighestWaterLevel:plInput + '最高水位',
    plInputlowestWterLevel:plInput + '最低水位',
    plInputsinglePlateQuantity:plInput + '单盘数量',
    plInputunitWeight:plInput + '单件重量',
    plInputunitPrice:plInput + '单价',
    plInputauxiliaryUnitOfMeasurement:plInput + '辅助计量单位',
    plSelectbuttressType:plSelect + '垛型',
    plInputmaterialDescription:plInput + '物料描述',
    plInputexternalSystemID:plInput + '外部系统ID',
  },
  //物料基础信息
  materialUnit:{
    unitName:'单位名称',
    enabled:'启用状态',
    plInputuniName:plInput + '单位名称'
  },
  //物料质量状态
  MaterialQualityStatus:{
    qalityStatusName:'质量状态名称',
    qalityStatusColor:'质量状态颜色',
    affiliationCompany:'所属公司',
    plInputqalityStatusName:plInput + '质量状态名称',
    color:'颜色'
  },
  //客户类别信息
  customTypeInfo:{
    typeNum:'类别编号',
    typeName:'类别名称',
    note:'备注',
    plInputtypeNum:plInput + '类别编号',
    plInputtypeName:plInput + '类别名称',
    plInputnote:plInput + '备注'
  },
  //客户基础信息
  customerBaseInfo:{
    customNum:'客户编号',
    customName:'客户名称',
    customType:'客户类型',
    contact:'联系人',
    contactType:'联系方式',
    address:'地址',
    plInputcustomNum:plInput + '客户编号',
    plInputcustomName:plInput + '客户名称',
    plSelectcustomType:plSelect + '客户类型',
    plInputcontact:plInput + '联系人',
  },
  //仓库基础信息
  warehouseBaseInfo:{
    warehouseCode:'仓库编码',
    warehouseName:'仓库名称',
    warehouseType:'仓库类型',
    slotType:'库位类型',
    taskEnableStatus:'任务启用状态',
    plInputwarehouseCode:plInput + '仓库编码',
    plInputwarehouseName:plInput + '仓库名称',
    plSelectslotType:plSelect + '库位类型',
  },
  //库区基础信息
  areaBasicInfo:{
    areaCode:'区域编码',
    inwarehouse:'所在仓库',
    areaName:'区域名称',
    plInputareaCode:plInput + '区域编码',
    plInputareaName:plInput + '区域名称',
  },
  //库位基础信息
  slotBasicInfo:{
    area:'区域',
    inventoryStatus:'库存状态',
    blockingState:'屏蔽状态',
    volume:'容积大小',
    storageLockStatus:'入库锁定状态',
    outboundLockStatus:'出库锁定状态',
    storageLock:'入库锁定',
    outboundLock:'出库锁定',
    shielding:'屏蔽',
    row:'排',
    cloumn:'列',
    layer:'层',
    slotCode:'库位编码',
    areaSelect:'区域选择',
    batchModify:'批量修改',
    tunnel:'巷道',
    isMove:'是否可移动'
  },
  //单据类型基础
  billType:{
    billTypeName:'单据类型名称',
    plInputbillTypeName:plInput + '单据类型名称',
    plSelectBillType:plSelect + '单据类型',
  },
  //出入口基础信息
  portBasiInfo:{
    portCode:'出入口编码',
    portName:'出入口名称',
    protType:'出入口类型',
    plInputportCode:plInput + '出入口编码',
    plInputportName:plInput + '出入口名称',
    plSelectprotName:plSelect + '出入口类型',
  },
  //月台基础信息
  plateFormBasicInfo:{
    plateFormNum:'月台编号',
    plateFormNanme:'月台名称',
    plInputplateFormNum:plInput + '月台编号',
    plInputplateFormNanme:plInput + '月台名称',
    plateFormStatus:'月台状态'
  },
  //垛型基础信息
  pacInfo:{
    packCode:'垛型编号',
    packName:'垛型名称',
    packImage:'垛型图片',
    plInputpackCode:plInput + '垛型编号',
    plInputpackName:plInput + '垛型名称',
  },
  //公司基础
  companyBasicInfo:{
    companyName:'公司名称',
    principal:'负责人',
    inputKeyWordToFilter:'输入关键字进行过滤',
    parent:'父级',
    detailAddress:'详细地址',
    plInputcompanyName:plInput + '公司名称',
    plInputprincipal:plInput+ '负责人',
    plInputNote:plInput + '备注'
  },
  //部门基础
  departmentBasicInfo:{
    departmentNo:'部门编号',
    departmentName:'部门名称',
    parentDepartment:'父级部门',
  },
  //人员基础信息
  staffInfo:{
    LoginName:'登录名',
    userName:'用户名',
    SubordinateDepartments:'所属部门',
    roleMulti:'角色(多选)',
    role:'角色',
    surnames:'姓氏',
    theName:'名字',
    password:'密码',
    plInpuntLoginName:plInput + '登录名',
    plInpuntuserName:plInput + '用户名',
    plSelectrole:plSelect + '角色',
    plInpuntsEurnames:plInput + '姓氏',
    plInpunttheName:plInput + '名字',
    plInpuntpassword:plInput + '密码',
    ToEnable:'启用',
    forbidden:'禁用',
    creationTime:'创建时间'
  },
  //角色信息A
  roleInfo:{
    roleName:'角色名称',
    roleType:'角色类型',
    roleAuthorization:'角色权限',
    plInputRoleName:plInput + '角色名称'
  },
  //系统模块设置
  systemModuleManage:{
    moduleName:'模块名称',
    icon:'图标',
    permissionIdentify:'权限标识',
    
    superior:'上级'
  },
  //库位生成
  slotInfo:{
    slotCreation:'库位生成',
    name:'名称',
    slotRow:'库位排',
    tunnelRow:'巷道排',
    InsideAndOutsideMark:'内外侧标识',
    CorrespondingLateralName:'对应外侧名',
    startLayer:'起始层',
    endLayer:'终止层',
    sort:'排序',
    startCloumn:'起始列',
    endCloumn:'终止列',
    plInputName:plInput + '名称',
    plInputSort:plInput + '排序',
    cloumnNum:'排号',
    plInputCloumnNum:plInput + '排号'
  },
  //入库单据管理
  importWarehouseManage:{
    check:'审核',
    AbandonTrial:'弃审',
    cancellation:'作废',
    AkeyAccount:'一键走账',
    billCompletion:'单据完结',
    createTask:'生成任务',
    billPrint:'单据打印',
    goodsBatchDetail:'物料批次明细',
    AnalogScanning:'模拟扫描',
    goodsCode:'物资编码',
    goodsName:'物资名称',
    bigBatchNum:'大批次号',
    smallBatchNum:'小批次号',
    planNum:'计划数量',
    boundNum:'绑定数量',
    completionNum:'完成数量',
    qalityStatus:'质量状态',
    importDate:'入库日期',
    checkStatus:'审核状态',
    cancellationStatus:'作废状态',
    PlInputcontractNum:plInput + '合同号'
  },
  //入库流水管理
  importBillBody:{
    bigBatch:'大批号',
    smallBatch:'小批号',
    executingStatus:'执行状态',
    batch:'批次',
    goodsPackDetail:'物资包装明细',
    packCode:'包装码',
    plInputbigBatch:plInput + '大批号',
    plInputsmallBatch:plInput + '小批号',
    plInputbatch:plInput + '批次',
    plInputquantity:plInput + '数量',
    validPeriod:'有效期'
  },
  //空托盘入库流水
  emptyStockImport:{
    stockCode:'托盘码',
    stockQuantity:'托盘数量'
  },
  //出库单据管理
  exportBillManage:{
    exportBillCode:'出库单号',
    settingWave:'设定波次',
    autoExport:'自动出库',
    manualExport:'手动出库',
    exportDate:'出库日期',
    plInputExportBillCode:plInput + '出库单号',
    waveNo:'波次号'
  },
  //出库单据流水
  exportBillBodyManage:{
    agency:'经销商',
    exportserialNo:'出库流水号',
    stock:'托盘',
    reCheck:'复核',
    slotCode:'库位码'
  },
  //任务列表
  taskList:{
    issueTime:'下发时间',
    source:'来源',
    manualCreate:'手动生成',
    modifyStatus:'修改状态',
    view:'查看',
    taskNo:'任务号',
    priority:'优先级',
  },
  //历史任务
  historyTask:{
    inAndOut:'入出口',
    returnNumber:'回流数量'
  },
  //监控策略管理
  monitorstrategyManage:{
    strategyName:'策略名称',
    overdueDays:'过期天数',
    MaximumAgeDays:'最大库龄天数',
    minimumInventory:'最小库存',
    maxInventory:'最大库存',
    reInspectioDays:'复检天数',
    plInputstrategyName:plInput + '策略名称',
    plInputoverdueDays:plInput + '过期天数',
    plInputMaximumAgeDays:plInput + '最大库龄天数',
    plInputminimumInventory:plInput + '最小库存',
    plInputreInspectioDays:plInput + '复检天数',
    plInputmaxInventory:plInput + '最大库存',
  },
  //上架策略管理
  putAwayStrategyManage:{
    avoidBusyLanes:'是否规避繁忙巷道',
    avoidBusyRoadwayPriority:'是否规避繁忙巷道优先级',
    rowChoice:'按排选择',
    RankPriority:'按排选择优先级',
    plInputpriority:plInput + '优先级',
    plSelectavoidBusyLanes:plSelect + '是否规避繁忙巷道',
    rowChoicesort:'按排选择顺序',
    plSelectrowChoicesort:plSelect + '按排选择顺序',
  },
  //分配策略管理
  distributionStrategyManage:{
    inAndOutSort:'入出顺序',
    ScreeningProgram:'筛选方案',
    firstExpirefirstOut:'先到期先出',
    isfFirstExpirefirstOut:'是否先到期先出',
    plSelectinAndOutSort:plSelect + '入出顺序',
    plSelectScreeningProgram:plSelect + '筛选方案',
    plSelectfirstExpirefirstOut:plSelect + '是否先到期先出',
  },
  //编码规则管理
  encodeRuleManage:{
    RulesCode:'规则编码',
    codeName:'编码名称',
    prefix:'前缀',
    dateType:'日期类型',
    fuffix:'后缀序列长度',
    plInputRulesCode:plInput + '规则编码',
    plInputcodeName:plInput + '编码名称',
    plInputprefix:plInput + '前缀',
    plSelectdateType:plSelect + '日期类型',
    plInputfuffix:plInput + '后缀序列长度',
  },
  //盘点管理
  inventoryManagement:{
    documentNo:'单据号',
    SubordinateWarehouse:'所属仓库',
    checkStatus:'盘点状态',
    plInputdocumentNo:plInput + '单据号',
    goods:'物资',
    filtrate:'筛选',
    checkProfit:'盘盈',
    checkLosses:'盘亏'
  },
  //总库存管理
  totalInventoryManage:{
    inventoryNum:'库存数'
  },
  //库位库存管理
  slotInventoryManage:{
    plInputslotCode:plInput + '库位编码',
    plInputpalletbarcode:plInput + '托盘条码',
    slotStatus:'库位状态',
    importTime:'入库时间'
  },
  //登录日志
  loginLog:{
    staff:'人员',
    operationTime:'操作时间',
    operationResult:'操作结果',
    plInputStaff:plInput + '人员'
  },
  //操作日志
  operationLog:{
    module:'模块',
    operationType:'操作类型',
    searchContent:'查询内容',
    beforModifyData:'修改前数据',
    afterModefyData:'修改后数据',
    plInputSearchContent:plInput + '查询内容'
  },
  //设备日志
  equipmentLog:{
    equipmentNo:'设备编号',
    equipmentName:'设备名称',
    operationMan:'操作人',
    logType:'日志类型',
    equipment:'设备',
    content:'内容',
    creationTime:'生成时间',
    plInputequipmentNo:plInput + '设备编号',
    plInputequipmentName:plInput + '设备名称',
  },
  //呆滞报警
  dullAlarm:{
    alarmName:'报警名称',
    thresholdValue:'阈值',
    alarmValue:'报警值',
    alarmStartTime:'报警开始时间',
  },
  //出入库台账
  exportImportStandingBook:{
    bills:'单据',
    balanceNum:'结存数量'
  },
  //出库报表
  exportRepotForm:{
    exportNum:'出库数量'
  },
  //仓库报表
  inventoryReportForm:{
    specification:'规格',
    inventoryNum:'库存数量'
  },
  //RGV
  RGV:{
    onLineState:'在线状态',
    alarmState:'报警状态'
  },
  // 抽检
  qualityCheckout:{
    checkoutBillNum:'抽检单号',
    checkoutNum:'抽检数量',
    originQualityStatus:'原质量状态',
    afterCheckoutQualityStatus:'检测后质量状态',
    checkoutDate:'抽检日期',
    createExportBills:'生成出库单',
    detection:'检测',
    qualityRelease:'质量放行',
  }


}

