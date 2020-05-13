export default {
  lang: {
    zh: '中文',
    en:'English',
    ja:'日語'
  },
  route: {
    dashboard: 'Dashboard',
    documentation: 'Documentation',
    BasicInfoManage: 'BasicInfoManage',
    SystemManageCenter: 'SystemManage',
    ImportWarehouseManage: 'ImportWarehouse',
    ExportWarehouseManage:'ExportWarehouse',
    TaskManage: 'TaskManage',
    BusinessManage: 'BusinessManage',
    InventoryManage: 'InventoryManage',
    LogManage: 'LogManage',
    WarehouseWarning: 'WarehouseWarning',
    WarehouseReport: 'WarehouseReport',
    WareEquipment: 'WareEquipment',
    QualityManagement: 'QualityManagement',

    //BasicInfoManage
    MaterialBasisInfo: 'MaterialBasisInfo',
    MaterialMeasureUnit: 'MaterialMeasureUnit',
    MaterialQualityStatus: 'MaterialQualityStatus',
    CustomerCategoryInfo: 'CustomerCategoryInfo',
    CustomerBaseInfo: 'CustomerBaseInfo',
    WarehouseBaseInfo: 'WarehouseBaseInfo',
    AreaBasicInfo: 'AreaBasicInfo',
    SlotBasicInfo: 'SlotBasicInfo',
    BillTypeInfo: 'BillTypeInfo',
    InOutdBasicInfo: 'InOutdBasicInfo',
    PlatFormBasicInfo: 'PlatFormBasicInfo',
    packBasicInfo: 'packBasicInfo',
    
    //SystemManageCenter
    CompanyBasicInfo: 'CompanyBasicInfo',
    DepartmentBasicInfo:'DepartmentBasicInfo',
    StaffBasicInfo: 'StaffBasicInfo',
    RoleBasicInfo: 'RoleBasicInfo',
    SystemModuleManage: 'SystemModuleManage',
    MoveModuleManage: 'MoveModuleManage',
    WarehouseBasicManage: 'WarehouseBasicManage',
    SlotInit: 'SlotInit',
    
    //ImportWarehouseManage
    ImportBillManage: 'ImportBillManage',
    ImportBillBodyManage: 'ImportBillBodyManage',
    EmptyStockImport: 'EmptyStockImport',

    //ExportWarehouseManage
    ExportBillManage: 'ExportBillManage',
    ExportBillBodyManage: 'ExportBillBodyManage',
    EmptyStockExport: 'EmptyStockExport',
    
    //TaskManage
    MainTaskManage: 'MainTaskManage',
    MainHistorytask: 'MainHistorytask',
    FlatBankTask:'FlatBankTask',
    PickingTask:'PickingTask',
    AGVTask:'AGVTask',
    RGVTask:'RGVTask',
    
    //BusinessManage
    StrategyMonitorManage: 'StrategyMonitorManage',
    StrategyPutawayManage: 'StrategyPutawayManage',
    StrategyDistriManage: 'StrategyDistriManage',
    EncodingRuleManage: 'EncodingRuleManage',
    
    //InventoryManage
    InventoryMaterialManage: 'InventoryMaterialManage',
    InventoryStockingManage: 'InventoryStockingManage',
    VisualizationManage: 'VisualizationManage',
    TotalInventoryManage: 'TotalInventoryManage',
    SlotInventoryManage: 'SlotInventoryManage',
    StockInventoryManage: 'StockInventoryManage',
    
    //LogManage
    LoginLogManage: 'LoginLogManage',
    OptLogManage: 'OptLogManage',
    MachineAlertLog: 'MachineAlertLog',
    ImportApplyLog:'ImportApplyLog',
    ExportBillSyncLog:'ExportBillSyncLog',
    
    //WarehouseWarning
    DullStockWarning: 'DullStockWarning',
    ValidityStockWarning: 'ValidityStockWarning',
    InventoryThresholdWarning:'InventoryThresholdWarning',
    ImportExceptionWarning:'ImportExceptionWarning',
    ExportExceptionWarning:'ExportExceptionWarning',
    InventoryExceptionWarning:'InventoryExceptionWarning',
    
    //WarehouseReport
    ExportImportStandingBook: 'ExportImportStandingBook',
    StorageUseRatio: 'StorageUseRatio',
    ImportReportForm: 'ImportReportForm',
    ExportReportForm:'ExportReportForm',
    inventoryReportForm: 'inventoryReportForm',
    
    //WareEquipment
    RGVStateManage: 'RGVStateManage',
    StackerStateManage:'StackerStateManage',

    //QualityManagement
    QualityReleased: 'QualityReleased',
    QualityCheckout: 'QualityCheckout',
  },
  navbar: {
    changePwd:"Change Password",
    logOut: 'Login Out',
    dashboard: 'Dashboard',
    github: 'Github',
    theme: 'Theme',
    size: 'Global Size',
    profile: 'Profile',
    administrator: 'Administrator',
    operation:'Operation',
    oldpsd:'Old Password',
    newpsd:'New Password',
    confirmpsd:'Confirm Password',
    newpsdnull:'New password cannot be empty',
    oldpsdnull:'Old password cannot be empty',
    confirmpsdnull:'Confirm password cannot be empty',
    psdnotmatch:'The two passwords are inconsistent',
    changepsdtip:'Are you sure want to change the password?',
    tiptitle:'Tips',
    changesucc:'The Password is changed',
    exitsys:'Are you sure want to exit the system？',
    systembusy:'System busy please try again later'
  },
  login: {
    title: 'Login Form',
    logIn: 'Login',
    username: 'Username',
    password: 'Password',
    any: 'any',
    thirdparty: 'Or connect with',
    thirdpartyTips: 'Can not be simulated on local, so please combine you own business simulation! ! !',
    forgotPassword: 'ForgotPassword',
    register: 'Register',
    fail:'Login Fail',
    success:'Login Success',
    loading:'loading',
    error:'login id or password error',
  },
  documentation: {
    documentation: 'Documentation',
    github: 'Github Repository'
  },
  permission: {
    createRole: 'New Role',
    editPermission: 'Edit',
    roles: 'Your roles',
    switchRoles: 'Switch roles',
    tips: 'In some cases, using v-permission will have no effect. For example: Element-UI  el-tab or el-table-column and other scenes that dynamically render dom. You can only do this with v-if.',
    delete: 'Delete',
    confirm: 'Confirm',
    cancel: 'Cancel',
    empty:'Empty',
    print:'Print',
    add:'Add'

  },
  guide: {
    description: 'The guide page is useful for some people who entered the project for the first time. You can briefly introduce the features of the project. Demo is based on ',
    button: 'Show Guide'
  },
  components: {
    documentation: 'Documentation',
    tinymceTips: 'Rich text is a core feature of the management backend, but at the same time it is a place with lots of pits. In the process of selecting rich texts, I also took a lot of detours. The common rich texts on the market have been basically used, and I finally chose Tinymce. See the more detailed rich text comparison and introduction.',
    stickyTips: 'when the page is scrolled to the preset position will be sticky on the top.',
    backToTopTips1: 'When the page is scrolled to the specified position, the Back to Top button appears in the lower right corner',
    backToTopTips2: 'You can customize the style of the button, show / hide, height of appearance, height of the return. If you need a text prompt, you can use element-ui el-tooltip elements externally',
    imageUploadTips: 'Since I was using only the vue@1 version, and it is not compatible with mockjs at the moment, I modified it myself, and if you are going to use it, it is better to use official version.'
  },
  table: {
    dynamicTips1: 'Fixed header, sorted by header order',
    dynamicTips2: 'Not fixed header, sorted by click order',
    dragTips1: 'The default order',
    dragTips2: 'The after dragging order',
    title: 'Title',
    importance: 'Importance',
    type: 'Type',
    remark: 'Remark',
    search: 'Search',
    export: 'Export',
    reviewer: 'Reviewer',
    id: 'ID',
    date: 'Date',
    author: 'Author',
    readings: 'Readings',
    status: 'Status',
    actions: 'Actions',
    edit: 'Edit',
    publish: 'Publish',
    draft: 'Draft',
    delete: 'Delete',
    cancel: 'Cancel',
    confirm: 'Confirm'
  },
  example: {
    warning: 'Creating and editing pages cannot be cached by keep-alive because keep-alive\'s \'include\' currently does not support caching based on routes, so now it\'s cached based on component name. If you want to achieve a similar caching effect, you can use a browser caching scheme such as localStorage. Or don\'t use keep-alive\'s \'include\' to cache all pages directly. See details'
  },
  errorLog: {
    tips: 'Please click the bug icon in the upper right corner',
    description: 'Now the management system are basically the form of the spa, it enhances the user experience, but it also increases the possibility of page problems, a small negligence may lead to the entire page deadlock. Fortunately Vue provides a way to catch handling exceptions, where you can handle errors or report exceptions.',
    documentation: 'Document introduction'
  },
  // excel: {
  //   export: 'Export',
  //   selectedExport: 'Export Selected Items',
  //   placeholder: 'Please enter the file name (default excel-list)'
  // },
  // zip: {
  //   export: 'Export',
  //   placeholder: 'Please enter the file name (default file)'
  // },
  pdf: {
    tips: 'Here we use window.print() to implement the feature of downloading PDF.'
  },
  theme: {
    change: 'Change Theme',
    documentation: 'Theme documentation',
    tips: 'Tips: It is different from the theme-pick on the navbar is two different skinning methods, each with different application scenarios. Refer to the documentation for details.'
  },
  tagsView: {
    refresh: 'Refresh',
    close: 'Close',
    closeOthers: 'Close Others',
    closeAll: 'Close All'
  },
  settings: {
    title: 'Page style setting',
    theme: 'Theme Color',
    showTagsView: 'Open Tags-View',
    showSidebarLogo: 'Sidebar Logo',
    fixedHeader: 'Fixed Header',
    sidebarTextTheme: 'Sidebar Text Theme',
    company: 'Jiangsu Xinmeixing Logistics Technology Co., LTD',
    WarehousingSystem:'WMS'
  },
  public:{
    stockcode:'stockcode',
    warehouse:'warehouse',
    select:'select',
    slot:'slot',
    quantity:'quantity',
    unit:'unit',
    plSelectunit:'plSelectunit',
    materialNum:'materialNum',
    pleaseInputMaterialNum:'pleaseInputMaterialNum',
    materialName:'materialName',
    pleaseInputMaterialName:'pleaseInputMaterialName',
    serialNumber:'serialNumber',
    plInput:'plInput',
    plSelect:'plSelect',
    saveDate:'save',
  },
  import: {
    stocknull:"Please enter pallet number",
    slotnull:"Please enter the location",
    quantitynull:"Please enter the number of pallets",
    receiptno:"Receipt No",
    contractno:"Contract No",
    supplier:"supplier",
    pleaseinput:"Please Input ",
    pleaseselect:"Please Select ",
    documenttype:"Document type",
    state:"State",
    warehouse:"Warehouse",
    documentdate:"Document date",
    startdate:"Start date",
    endtdate:"End date",
    search:"Search",
    palletstatus:"Pallet status",
    materialquantity:"Material quantity",
    palletbarcode:"Pallet barcode",
  },
  //materialBase
  materialBase:{
    storageArea:'storageArea',
    plSelectStorageArea:'plSelectStorageArea',
    monitoringStrategy:'monitoringStrategy',
    plSelectMonitoringStrategy:'plSelectMonitoringStrategy',
    allocationStrategy:'allocationStrategy',
    plSelectAllocationStrategy:'plSelectAllocationStrategy',
    saleStrategy:'saleStrategy',
    plSelectSaleStrategy:'plSelectSaleStrategy',
    bigPackageNumber:'bigPackageNumber',
    midPackageNumber:'midPackageNumber',
    smallPackageNumber:'smallPackageNumber',
    materialType:'materialType',
    type:'type',
    abbreviation:'abbreviation',
    englishName:'englishName',
    bigPackageQuantity:'bigPackageQuantity',
    midPackageQuantity:'midPackageQuantity',
    smallPackageQuantity:'smallPackageQuantity',
    highestWaterLevel:'highestWaterLevel',
    lowestWterLevel:'lowestWterLevel',
    singlePlateQuantity:'singlePlateQuantity',
    unitWeight:'unitWeight',
    unitPrice:'unitPrice',
    auxiliaryUnitOfMeasurement:'auxiliaryUnitOfMeasurement',
    PeriodOfValidityDay:'PeriodOfValidity(shelf life, days)',
    buttressType:'buttressType',
    materialDescription:'materialDescription',
    externalSystemID:'externalSystemID',
    materialPicture:'materialPicture',

    plSelectmaterialType:'plSelectmaterialType',
    plInputtype:'plInputtype',
    plInputabbreviation:'plInputabbreviation',
    plInputenglishName:'plInputenglishName',
    plInputbigPackageQuantity:'plInputbigPackageQuantity',
    plInputmidPackageQuantity:'plInputmidPackageQuantity',
    plInputsmallPackageQuantity:'plInputsmallPackageQuantity',
    plInputhighestWaterLevel:'plInputhighestWaterLevel',
    plInputlowestWterLevel:'plInputlowestWterLevel',
    plInputsinglePlateQuantity:'plInputsinglePlateQuantity',
    plInputunitWeight:'plInputunitWeight',
    plInputunitPrice:'plInputunitPrice',
    plInputauxiliaryUnitOfMeasurement:'plInputauxiliaryUnitOfMeasurement',
    plSelectbuttressType:'plSelectbuttressType',
    plInputmaterialDescription:'plInputmaterialDescription',
    plInputexternalSystemID:'plInputexternalSystemID',
  },
   //materialUnit
   materialUnit:{
    unitName:'unitName',
    enabled:'enabled',
    plInputuniName:'plInputuniName'
  },
   //MaterialQualityStatus
   MaterialQualityStatus:{
    qalityStatusName:'qalityStatusName',
    qalityStatusColor:'qalityStatusColor',
    affiliationCompany:'affiliationCompany',
    plInputqalityStatusName:'plInputqalityStatusName',
    color:'color'
  },
  //customTypeInfo
  customTypeInfo:{
    typeNum:'typeNum',
    typeName:'typeName',
    note:'note',
    plInputtypeNum:'plInputtypeNum',
    plInputtypeName:'plInputtypeName',
    plInputnote:'plInputnote'
  },
  //customerBaseInfo
  customerBaseInfo:{
    customNum:'customNum',
    customName:'customName',
    customType:'customType',
    contact:'contact',
    contactType:'contactType',
    address:'address',
    plInputcustomNum:'plInputcustomNum',
    plInputcustomName:'plInputcustomName',
    plSelectcustomType:'plSelectcustomType',
    plInputcontact:'plInputcontact',
  },
  //warehouseBaseInfo
  warehouseBaseInfo:{
    warehouseCode:'warehouseCode',
    warehouseName:'warehouseName',
    warehouseType:'warehouseType',
    slotType:'slotType',
    taskEnableStatus:'taskEnableStatus',
    plInputwarehouseCode:'plInputwarehouseCode',
    plInputwarehouseName:'plInputwarehouseName',
    plSelectslotType:'plSelectslotType',
  },
   //slotBasicInfo
   areaBasicInfo:{
    areaCode:'areaCode',
    inwarehouse:'inwarehouse',
    areaName:'areaName',
    plInputareaCode:'plInputareaCode',
    plInputareaName:'plInputareaName',
  },
  //slotBasicInfo
  slotBasicInfo:{
    area:'area',
    inventoryStatus:'inventoryStatus',
    blockingState:'blockingState',
    volume:'volume',
    storageLockStatus:'storageLockStatus',
    outboundLockStatus:'outboundLockStatus',
    shielding:'shielding',
    row:'row',
    cloumn:'cloumn',
    layer:'layer',
    slotCode:'slotCode',
    storageLock:'storageLock',
    outboundLock:'outboundLock',
    areaSelect:'areaSelect',
    batchModify:'batchModify',
    tunnel:'tunnel',
    isMove:'isMove'
  },
   //billType
   billType:{
    billTypeName:'billTypeName',
    plInputbillTypeName:'plInputbillTypeName',
    plSelectBillType:'plSelectBillType',
  },
  //portBasiInfo
  portBasiInfo:{
    portCode:'portCode',
    portName:'portName',
    protType:'protType',
    plInputportCode:'plInputportCode',
    plInputportName:'plInputportName',
    plSelectprotName:'plSelectprotName',
  },
  //plateFormBasicInfo
  plateFormBasicInfo:{
    plateFormNum:'plateFormNum',
    plateFormNanme:'plateFormNanme',
    plInputplateFormNum:'plInputplateFormNum',
    plInputplateFormNanme:'plInputplateFormNanme',
    plateFormStatus:'plateFormStatus'
  },
  //pacInfo
  pacInfo:{
    packCode:'packCode',
    packName:'packName',
    packImage:'packImage',
    plInputpackCode:'plInputpackCode',
    plInputpackName:'plInputpackName',
  },
  //companyBasicInfo
  companyBasicInfo:{
    companyName:'companyName',
    principal:'principal',
    inputKeyWordToFilter:'inputKeyWordToFilter',
    parent:'parent',
    detailAddress:'detailAddress',
    plInputcompanyName:'plInputcompanyName',
    plInputprincipal:'plInputprincipal',
    plInputNote:'plInputNote'
  },
   //departmentBasicInfo
   departmentBasicInfo:{
    departmentNo:'departmentNo',
    departmentName:'departmentName',
    parentDepartment:'parentDepartment',
  },
   //staffInfo
   staffInfo:{
    LoginName:'LoginName',
    userName:'userName',
    SubordinateDepartments:'SubordinateDepartments',
    roleMulti:'role(multi-select)',
    role:'role',
    surnames:'surnames',
    theName:'theName',
    password:'password',
    plInpuntLoginName:'plInpuntLoginName',
    plInpuntuserName:'plInpuntuserName',
    plSelectrole:'plSelectrole',
    plInpuntsurnames:'plInpuntsurnames',
    plInpunttheName:'plInpunttheName',
    plInpuntpassword:'plInpuntpassword',
    ToEnable:'ToEnable',
    forbidden:'forbidden',
    creationTime:'creationTime'
  },
   //roleInfo
   roleInfo:{
    roleName:'roleName',
    roleType:'roleType',
    roleAuthorization:'roleAuthorization',
    plInputRoleName:'plInputRoleName'
  },
  //systemModuleManage
  systemModuleManage:{
    moduleName:'moduleName',
    icon:'icon',
    permissionIdentify:'permissionIdentify',
    superior:'superior'
  },
  //slotInfo
  slotInfo:{
    slotCreation:'slotCreation',
    name:'name',
    slotRow:'slotRow',
    tunnelRow:'tunnelRow',
    InsideAndOutsideMark:'InsideAndOutsideMark',
    CorrespondingLateralName:'CorrespondingLateralName',
    startLayer:'startLayer',
    endLayer:'endLayer',
    sort:'sort',
    startCloumn:'startCloumn',
    endCloumn:'endCloumn',
    plInputName:'plInputName',
    plInputSort:'plInputSort',
    cloumnNum:'cloumnNum',
    plInputCloumnNum:'plInputCloumnNum'
  },
   //importWarehouseManage
   importWarehouseManage:{
    check:'check',
    AbandonTrial:'AbandonTrial',
    cancellation:'cancellation',
    AkeyAccount:'AkeyAccount',
    billCompletion:'billCompletion',
    createTask:'createTask',
    billPrint:'billPrint',
    goodsBatchDetail:'goodsBatchDetail',
    AnalogScanning:'AnalogScanning',
    goodsCode:'goodsCode',
    goodsName:'goodsName',
    bigBatchNum:'bigBatchNum',
    smallBatchNum:'smallBatchNum',
    planNum:'planNum',
    boundNum:'boundNum',
    completionNum:'completionNum',
    qalityStatus:'qalityStatus',
    importDate:'importDate',
    checkStatus:'checkStatus',
    cancellationStatus:'cancellationStatus',
    PlInputcontractNum:'PlInputcontractNum',
  },
   //importBillBody
   importBillBody:{
    bigBatch:'bigBatch',
    smallBatch:'smallBatch',
    executingStatus:'executingStatus',
    batch:'batch',
    goodsPackDetail:'goodsPackDetail',
    packCode:'packCode',
    plInputbigBatch:'plInputbigBatch',
    plInputsmallBatch:'plInputsmallBatch',
    plInputbatch:'plInputbatch',
    plInputquantity:'plInputquantity',
    validPeriod:'validPeriod'
  },
  //emptyStockImport
  emptyStockImport:{
    stockCode:'stockCode',
    stockQuantity:'stockQuantity'
  },
  //出库单据管理
  exportBillManage:{
    exportBillCode:'exportBillCode',
    settingWave:'settingWave',
    autoExport:'autoExport',
    manualExport:'manualExport',
    exportDate:'exportDate',
    plInputExportBillCode:'plInputExportBillCode',
    waveNo:'waveNo'
  },
  //exportBillBodyManage
  exportBillBodyManage:{
    agency:'agency',
    exportserialNo:'exportserialNo',
    stock:'stock',
    reCheck:'reCheck',
    slotCode:'slotCode'
  },
  //taskList
  taskList:{
    issueTime:'issueTime',
    source:'source',
    manualCreate:'manualCreate',
    modifyStatus:'modifyStatus',
    view:'view',
    taskNo:'taskNo',
    priority:'priority',
  },
  //historyTask
  historyTask:{
    inAndOut:'inAndOut',
    returnNumber:'returnNumber'
  },
  //监控策略管理
  monitorstrategyManage:{
    strategyName:'strategyName',
    overdueDays:'overdueDays',
    MaximumAgeDays:'MaximumAgeDays',
    minimumInventory:'minimumInventory',
    maxInventory:'maxInventory',
    reInspectioDays:'reInspectioDays',
    plInputstrategyName:'plInputstrategyName',
    plInputoverdueDays:'plInputoverdueDays',
    plInputMaximumAgeDays:'plInputMaximumAgeDays',
    plInputminimumInventory:'plInputminimumInventory',
    plInputreInspectioDays:'plInputreInspectioDays',
    plInputmaxInventory:'plInputmaxInventory',
  },
  //putAwayStrategyManage
  putAwayStrategyManage:{
    avoidBusyLanes:'avoidBusyLanes',
    avoidBusyRoadwayPriority:'avoidBusyRoadwayPriority',
    rowChoice:'rowChoice',
    RankPriority:'RankPriority',
    plInputpriority:'plInputpriority',
    plSelectavoidBusyLanes:'plSelectavoidBusyLanes',
    rowChoicesort:'rowChoicesort',
    plSelectrowChoicesort:'plSelectrowChoicesort',
  },
  //distributionStrategyManage
  distributionStrategyManage:{
    inAndOutSort:'inAndOutSort',
    ScreeningProgram:'ScreeningProgram',
    firstExpirefirstOut:'firstExpirefirstOut',
    isfFirstExpirefirstOut:'isfFirstExpirefirstOut',
    plSelectinAndOutSort:'plSelectinAndOutSort',
    plSelectScreeningProgram:'plSelectScreeningProgram',
    plSelectfirstExpirefirstOut:'plSelectfirstExpirefirstOut',
  },
  //encodeRuleManage
  encodeRuleManage:{
    RulesCode:'RulesCode',
    codeName:'codeName',
    prefix:'prefix',
    dateType:'dateType',
    fuffix:'fuffix',
    plInputRulesCode:'plInputRulesCode',
    plInputcodeName:'plInputcodeName',
    plInputprefix:'plInputprefix',
    plSelectdateType:'plSelectdateType',
    plInputfuffix:'plInputfuffix',
  },
  //inventoryManagement
  inventoryManagement:{
    documentNo:'documentNo',
    SubordinateWarehouse:'SubordinateWarehouse',
    checkStatus:'checkStatus',
    plInputdocumentNo:'plInputdocumentNo',
    goods:'goods',
    filtrate:'filtrate',
    checkProfit:'checkProfit',
    checkLosses:'checkLosses'
  },
  //总库存管理
  totalInventoryManage:{
    inventoryNum:'inventoryNum'
  },
  //slotInventoryManage
  slotInventoryManage:{
    plInputslotCode:'plInputslotCode',
    plInputpalletbarcode:'plInputpalletbarcode',
    slotStatus:'slotStatus',
    importTime:'importTime'
  },
   //loginLog
   loginLog:{
    staff:'staff',
    operationTime:'operationTime',
    operationResult:'operationResult',
    plInputStaff:'plInputStaff'
  },
  //operationLog
  operationLog:{
    module:'module',
    operationType:'operationType',
    searchContent:'searchContent',
    beforModifyData:'beforModifyData',
    afterModefyData:'afterModefyData',
    plInputSearchContent:'plInputSearchContent'
  },
  //equipmentLog
  equipmentLog:{
    equipmentNo:'equipmentNo',
    equipmentName:'equipmentName',
    operationMan:'operationMan',
    logType:'logType',
    equipment:'equipment',
    content:'content',
    creationTime:'creationTime',
    plInputequipmentNo:'plInputequipmentNo',
    plInputequipmentName:'plInputequipmentName',
  },
   //dullAlarm
   dullAlarm:{
    alarmName:'alarmName',
    thresholdValue:'thresholdValue',
    alarmValue:'alarmValue',
    alarmStartTime:'alarmStartTime',
  },
  //exportImportStandingBook
  exportImportStandingBook:{
    bills:'bills',
    balanceNum:'balanceNum'
  },
  //exportRepotForm
  exportRepotForm:{
    exportNum:'exportNum'
  },
  //inventoryReportForm
  inventoryReportForm:{
    specification:'specification',
    inventoryNum:'inventoryNum'
  },
  //RGV
  RGV:{
    onLineState:'onLineState',
    alarmState:'alarmState'
  },
  // qualityCheckout
  qualityCheckout:{
    checkoutBillNum:'checkoutBillNum',
    checkoutNum:'checkoutNum',
    originQualityStatus:'originQualityStatus',
    afterCheckoutQualityStatus:'afterCheckoutQualityStatus',
    checkoutDate:'checkoutDate',
    createExportBills:'createExportBills',
    detection:'detection',
    qualityRelease:'qualityRelease',
  }


}
