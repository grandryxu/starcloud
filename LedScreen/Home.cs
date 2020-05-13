using DAO;
using Model;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using NVRCsharpDemo;
using Service;
using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.IO;
using System.IO.Ports;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace LedScreen
{
    public partial class Home : Form
    {

        public delegate void HandleInterfaceUpdataDelegate(string text); //委托，此为重点 
        private HandleInterfaceUpdataDelegate interfaceUpdataHandle;
        private WorkerService workerService;
        private Color RowBackColorAlt = Color.FromArgb(32, 32, 48);//交替色 
        private Color RowBackColorSel = Color.FromArgb(32, 32, 48);//选择项目颜色 
        private Color BorderColor = Color.FromArgb(178, 178, 185);
        private ArrayList serialPortsList;
        private int Step = 0;

        /*****视频监控部分******/
        private bool m_bInitSDK = false;
        //private bool m_bRecord = false;
        private uint iLastErr = 0;
        private Int32 m_lUserID = -1;
        private Int32 m_lRealHandle = -1;
        private string str1;
        private string str2;
        private Int32 i = 0;
        private Int32 m_lTree = 0;
        private string str;
        private uint dwAChanTotalNum = 0;
        private uint dwDChanTotalNum = 0;
        private int[] iIPDevID = new int[96];
        private int[] iChannelNum = new int[96];
        public CHCNetSDK.NET_DVR_DEVICEINFO_V30 DeviceInfo;
        public CHCNetSDK.NET_DVR_IPPARACFG_V40 m_struIpParaCfgV40;
        public CHCNetSDK.NET_DVR_STREAM_MODE m_struStreamMode;
        public CHCNetSDK.NET_DVR_IPCHANINFO m_struChanInfo;
        public CHCNetSDK.NET_DVR_IPCHANINFO_V40 m_struChanInfoV40;
        public delegate void MyDebugInfo(string str);

        /*****以上为视频监控所需字段*******/

        //出勤人员头像图片数组
        private List<Image> images = new List<Image>();
        static DataTable dtb = new DataTable();
        private DataRow currentUserInfo = dtb.NewRow();
        string pname = "";

        double formWidth;//窗体原始宽度
        double formHeight;//窗体原始高度
        double scaleX;//水平缩放比例
        double scaleY;//垂直缩放比例
        Dictionary<string, string> ControlsInfo = new Dictionary<string, string>();//控件中心Left,Top,控件Width,控件Height,控件字体Size

        protected void GetAllInitInfo(Control ctrlContainer)
        {
            if (ctrlContainer.Parent == this)//获取窗体的高度和宽度
            {
                formWidth = Convert.ToDouble(ctrlContainer.Width);
                formHeight = Convert.ToDouble(ctrlContainer.Height);
            }
            foreach (Control item in ctrlContainer.Controls)
            {
                if (item.Name.Trim() != "")
                {
                    //添加信息：键值：控件名，内容：据左边距离，距顶部距离，控件宽度，控件高度，控件字体。
                    ControlsInfo.Add(item.Name, (item.Left + item.Width / 2) + "," + (item.Top + item.Height / 2) + "," + item.Width + "," + item.Height + "," + item.Font.Size);
                }
                if ((item as UserControl) == null && item.Controls.Count > 0)
                {
                    GetAllInitInfo(item);
                }
            }

        }
        private void ControlsChaneInit(Control ctrlContainer)
        {
            scaleX = (Convert.ToDouble(ctrlContainer.Width) / formWidth);
            scaleY = (Convert.ToDouble(ctrlContainer.Height) / formHeight);
        }
        /// <summary>
        /// 改变控件大小
        /// </summary>
        /// <param name="ctrlContainer"></param>
        private void ControlsChange(Control ctrlContainer)
        {
            double[] pos = new double[5];//pos数组保存当前控件中心Left,Top,控件Width,控件Height,控件字体Size
            foreach (Control item in ctrlContainer.Controls)//遍历控件
            {
                if (item.Name.Trim() != "")//如果控件名不是空，则执行
                {
                    if ((item as UserControl) == null && item.Controls.Count > 0)//如果不是自定义控件
                    {
                        ControlsChange(item);//循环执行
                    }
                    string[] strs = ControlsInfo[item.Name].Split(',');//从字典中查出的数据，以‘，’分割成字符串组

                    for (int i = 0; i < 5; i++)
                    {
                        pos[i] = Convert.ToDouble(strs[i]);//添加到临时数组
                    }
                    double itemWidth = pos[2] * scaleX;     //计算控件宽度，double类型
                    double itemHeight = pos[3] * scaleY;    //计算控件高度
                    item.Left = Convert.ToInt32(pos[0] * scaleX - itemWidth / 2);//计算控件距离左边距离
                    item.Top = Convert.ToInt32(pos[1] * scaleY - itemHeight / 2);//计算控件距离顶部距离
                    item.Width = Convert.ToInt32(itemWidth);//控件宽度，int类型
                    item.Height = Convert.ToInt32(itemHeight);//控件高度
                    float emSize = float.Parse((pos[4] * Math.Min(scaleX, scaleY)).ToString());
                    if (emSize > System.Single.MaxValue)
                        emSize = System.Single.MaxValue;
                    if (emSize <= 0)
                        emSize = 12;
                    item.Font = new Font(item.Font.Name, emSize);//字体
                    item.Refresh();
                }
            }

        }
        /// <summary>
        /// 后台更新线上数据
        /// </summary>
        private BackgroundWorker worker = new BackgroundWorker();
        private void initWorker()
        {
            worker.WorkerReportsProgress = true;
            worker.WorkerSupportsCancellation = true;
            worker.DoWork += new DoWorkEventHandler(DoWork);
            worker.ProgressChanged += new ProgressChangedEventHandler(ProgessChanged);
            //worker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(CompleteWork);
        }
        public void DoWork(object sender, DoWorkEventArgs e)
        {
            if (worker.CancellationPending)
            {
                e.Cancel = true;
                return;
            }
            worker.ReportProgress(1);
            try
            {
                GetJobCountLEDInfoAsync();//工种线上实时
                GetGroupCountLEDInfo();//班组线上实时
                UpdateWorkerNumberOnlineAsync();//项目线上考勤
                GetProjectCountInfoAsync();//项目线上人数
                GetAllUsers(ConfigurationManager.AppSettings["prorealname"].ToString());
                worker.ReportProgress(100);
            }
            catch
            {
                e.Cancel = true;
            }
            
        }
        /// <summary>
        /// 工种线上实时
        /// </summary>
        private void GetJobCountLEDInfoAsync()
        {
            string postURL = ConfigurationManager.AppSettings["getRecordInfoByWork"].ToString();
            string data = JObject.FromObject(new
            {
                projectName = ConfigurationManager.AppSettings["prorealname"].ToString()
            }).ToString();
            Task<string> rr = CommonUtil.GetResponseAsync(postURL, data);
            string task = rr.Result;
            Console.WriteLine("GetJobCountLEDInfoAsync:" + task);
            JObject jj = JObject.Parse(task);
            IList<JToken> results = jj["record"].Children().ToList();

            IList<Record> searchResults = new List<Record>();
            foreach (JToken result in results)
            {
                Record searchResult = result.ToObject<Record>();
                searchResults.Add(searchResult);
            }
            string outp = string.Empty;
            foreach (Record r in searchResults)
            {
                string output = "";
                output = "考勤人数：" + r.count.ToString();
                outp += r.workKindName + output + ",";
            }
            if (!string.Empty.Equals(outp))
                CommonUtil.updateAsyncData("GetJobCountLEDInfoAsync", outp);

        }
        /// <summary>
        /// 班组线上实时
        /// </summary>
        private void GetGroupCountLEDInfo()
        {
            string postURL = ConfigurationManager.AppSettings["getRecordInfoByClassNo"].ToString();
            string data = JObject.FromObject(new
            {
                projectName = ConfigurationManager.AppSettings["prorealname"].ToString()
            }).ToString();

            string task = CommonUtil.GetResponseAsync(postURL, data).Result;
            JObject jj = JObject.Parse(task);
            IList<JToken> results = jj["record"].Children().ToList();
            List<string> rr = new List<string>();
            foreach (JToken result in results)
            {
                ClassCount searchResult = result.ToObject<ClassCount>();
                rr.Add(string.Format("班组：{0} 考勤人数：{1}", searchResult.classNo, searchResult.count));
            }
            if (rr.Count > 0)
            {
                Console.WriteLine(string.Join("，", rr));
                CommonUtil.updateAsyncData("GetGroupCountLEDInfoAsync", string.Join("，", rr));
            }
        }
        /// <summary>
        /// 项目线上考勤
        /// </summary>
        private void UpdateWorkerNumberOnlineAsync()
        {
            string postURL = ConfigurationManager.AppSettings["getRecordInfoByProject"].ToString();
            string data = JObject.FromObject(new
            {
                projectName = ConfigurationManager.AppSettings["prorealname"].ToString()
            }).ToString();
            
            string task = CommonUtil.GetResponseAsync(postURL, data).Result;
            JObject jj = JObject.Parse(task);
            if (!string.Empty.Equals(jj["count"].ToObject<string>()))
                CommonUtil.updateAsyncData("UpdateWorkerNumberOnlineAsync", "考勤人数：" + jj["count"].ToObject<string>());
        }
        /// <summary>
        /// 项目线上人数
        /// </summary>
        private void GetProjectCountInfoAsync()
        {
            string postURL = ConfigurationManager.AppSettings["postCountURL"].ToString();
            /*** 实名制
            string data = JObject.FromObject(new
            {
                userInfo = new
                {
                    projectName = ConfigurationManager.AppSettings["prorealname"].ToString()
                }
            }).ToString();
            ***/
            //劳务通
            string data = JObject.FromObject(new { projectName = ConfigurationManager.AppSettings["prorealname"].ToString() }).ToString();


            string task = CommonUtil.GetResponseAsync(postURL,data).Result;

            JObject jj = JObject.Parse(task);
            if(!string.Empty.Equals(jj["record"].ToString()))
                CommonUtil.updateAsyncData("GetProjectCountInfoAsync", "项目人数：" + jj["record"].ToString());
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void ProgessChanged(object sender, ProgressChangedEventArgs e)
        {
            //Console.WriteLine(String.Format("ProgessChanged###{0}", e.ProgressPercentage));
            if (e.ProgressPercentage == 100)
                Console.WriteLine("后台任务运行成功！");
            //MessageBox.Show("成功！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }
        public void CompleteWork(object sender, RunWorkerCompletedEventArgs e)
        {
            Console.WriteLine(String.Format("CompleteWork###{0}", e.Result));
        }
        public Home()
        {
            InitializeComponent();

            workerService = new WorkerService();
            //lstBoxWorker.DrawMode = DrawMode.OwnerDrawVariable;
            //lstBoxWorker.ItemHeight = 100;
            serialPortsList = new ArrayList();
            OpenCameraWatch();
            pname = CommonUtil.GetConfigValue("prorealname");//确保填写正确，用于远程获取时传参
            this.labproname.Text = CommonUtil.GetConfigValue("proname");//用于定制显示项目名;
            this.slogen.Text = CommonUtil.GetConfigValue("slogen");
            this.foundation.Text = CommonUtil.GetConfigValue("foundation");


            //this.FormBorderStyle = FormBorderStyle.None;     //设置窗体为无边框样式
            this.WindowState = FormWindowState.Maximized;    //最大化窗体 
            this.AutoScaleMode = AutoScaleMode.Font;
            tlp.GetType().GetProperty("DoubleBuffered", System.Reflection.BindingFlags.Instance | System.Reflection.BindingFlags.NonPublic).SetValue(tlp, true, null);

            GetcountFromURL(pname);
            UpadteAllAtUsers(pname);

            GetAllInitInfo(this.Controls[0]);
            this.GetWeatherAsync();
            this.logo();

            this.labelTime.Text = DateTime.Now.ToString("yyyy年 MM月dd日 dddd");
            this.timenow.Text = DateTime.Now.ToString("T");

            SQLiteDBHelper.CreateTable(CommonUtil.asyncdatasql);
            initWorker();
        }

        private void logo()
        {
            try
            {
                this.logopic.BackgroundImage = Image.FromFile(Application.StartupPath + @"\Resources\logo.png");
                this.panel4.BackgroundImage = Image.FromFile(Application.StartupPath + @"\Resources\bg.png");
            }
            catch (Exception ex) { this.logopic.Visible = false; this.logopic.Dispose(); Console.WriteLine(ex.Message); }
        }

        /// <summary>
        /// 查询各工种考勤统计信息
        /// </summary>
        private void UpdateWorkerJobCount()
        {
            string sql = "select distinct job from worker where groupname='" + ConfigurationManager.AppSettings["prorealname"].ToString() + "';";
            DataTable dt = SQLiteDBHelper.ExecuteDataTable(sql);
            if (dt.Rows.Count <= 0)
                return;

            if (this.Step < Math.Ceiling((double)dt.Rows.Count / 10))
            {
                tlp.Controls.Clear();
                int s = this.Step * 10;
                for (int i = s; i < 10 * (this.Step + 1); i++)
                {
                    if (i < dt.Rows.Count)
                    {

                        jobLableGroup(dt, i);
                    }

                }
                this.Step++;
            }
            else
            {
                this.Step = 0;
            }

        }
        private void jobLableGroup(DataTable dt, int i)
        {
            try
            {
                Label lab = new Label();
                string countsql = string.Format("select count(id) as count from worker where groupname = '{0}' and job = '{1}' and DATE(checkinTime) = '{2}';",
                    pname,
                    dt.Rows[i]["job"].ToString(),
                    DateTime.Now.ToString("yyyy-MM-dd")
                    );
                DataTable dtt = SQLiteDBHelper.ExecuteDataTable(countsql);
                string output = "";
                if (dtt.Rows.Count > 0)
                    output = "\n考勤人数：" + dtt.Rows[0]["count"].ToString();
                lab.Text = dt.Rows[i]["job"].ToString() + output;
                lab.AutoSize = true;
                FontFamily ff = new FontFamily("微软雅黑");
                //lab.Font = new Font(ff, 16, FontStyle.Regular, GraphicsUnit.World);
                //通过Anchor 设置Label 列居中
                lab.Anchor = (AnchorStyles.Left | AnchorStyles.Right);
                tlp.Controls.Add(lab);
            }
            catch (Exception ex) { Console.WriteLine(string.Format("考勤人数查询出错：{0}", ex.Message)); }
        }
        /// <summary>
        /// 线上查询各工种考勤统计信息
        /// </summary>
        private async void UpdateWorkerJobCountOnlineAsync()
        {
            string postURL = ConfigurationManager.AppSettings["getRecordInfoByWork"].ToString();
            string data = JObject.FromObject(new
            {
                projectName = ConfigurationManager.AppSettings["prorealname"].ToString()
            }).ToString();
            try
            {
                string task = await CommonUtil.GetResponseAsync(postURL, data);
                JObject jj = JObject.Parse(task);
                IList<JToken> results = jj["record"].Children().ToList();
                IList<Record> searchResults = new List<Record>();
                foreach (JToken result in results)
                {
                    Record searchResult = result.ToObject<Record>();
                    searchResults.Add(searchResult);
                }

                if (this.Step < Math.Ceiling((double)searchResults.Count / 10))
                {
                    tlp.Controls.Clear();
                    int s = this.Step * 10;
                    for (int i = s; i < 10 * (this.Step + 1); i++)
                    {

                        if (i < searchResults.Count)
                        {

                            setJobCountGroup(searchResults[i]);
                        }
                    }
                    this.Step++;
                }
                else
                {
                    this.Step = 0;
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("***" + ex.Message);
            }
        }
        /// <summary>
        /// 线上班组考勤统计查询
        /// </summary>
        private async void UpdateWorkerClassCountOnlineAsync()
        {
            MessageBox.Show("-------UpdateWorkerClassCountOnlineAsync");
            string postURL = ConfigurationManager.AppSettings["getRecordInfoByClassNo"].ToString();
            string data = JObject.FromObject(new
            {
                projectName = ConfigurationManager.AppSettings["prorealname"].ToString()
            }).ToString();
            try
            {
                string task = await CommonUtil.GetResponseAsync(postURL, data);


                //Console.WriteLine("线上班组考勤统计查询：" + task);
                JObject jj = JObject.Parse(task);
                IList<JToken> results = jj["record"].Children().ToList();
                IList<ClassCount> searchResults = new List<ClassCount>();
                foreach (JToken result in results)
                {
                    ClassCount searchResult = result.ToObject<ClassCount>();
                    Console.WriteLine(searchResult.classNo);
                    searchResults.Add(searchResult);
                }
                MessageBox.Show("--------"+this.Step+"-----"+ Math.Ceiling((double)searchResults.Count / 10));
                if (this.Step < Math.Ceiling((double)searchResults.Count / 10))
                {
                    tlp.Controls.Clear();
                    int s = this.Step * 10;
                    for (int i = s; i < 10 * (this.Step + 1); i++)
                    {

                        if (i < searchResults.Count)
                        {
                            Console.WriteLine("开始调用：" + i);
                            setClassNoCountGroup(searchResults[i]);
                        }
                    }
                    Step++;
                    Console.WriteLine("Step = {0}", Step);
                }
                else
                {
                    Step = 0;
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("***" + ex.Message);
            }
        }
        private void setJobCountGroup(Record r)
        {

            Label lab = new Label();
            string output = "";

            output = "\n考勤人数：" + r.count.ToString();
            lab.Text = r.workKindName + output;
            lab.AutoSize = true;
            FontFamily ff = new FontFamily("微软雅黑");
            //lab.Font = new Font(ff, 16, FontStyle.Regular, GraphicsUnit.World);
            //通过Anchor 设置Label 列居中
            lab.Anchor = (AnchorStyles.Left | AnchorStyles.Right);
            tlp.Controls.Add(lab);

        }
        private void setClassNoCountGroup(ClassCount r)
        {
          //  MessageBox.Show("hhahaah"+r.classNo);
            Label lab = new Label();
            string output = "";

            output = "\n考勤人数：" + r.count.ToString();
            lab.Text = r.classNo + output;
            lab.AutoSize = true;
            FontFamily ff = new FontFamily("微软雅黑");
            //lab.Font = new Font(ff, 16, FontStyle.Regular, GraphicsUnit.World);
            //通过Anchor 设置Label 列居中
            lab.Anchor = (AnchorStyles.Left | AnchorStyles.Right);
            tlp.Controls.Add(lab);
            Console.WriteLine("tlp.Controls 共有：{0}" + tlp.Controls.Count);
        }
        /// <summary>
        /// 在线获取项目考勤人数
        /// </summary>
        /// <param name="pname">项目名</param>
        public async void GetcountFromURL(string pname)
        {
            /*** 实名制
            string data = JObject.FromObject(new
            {
                userInfo = new
                {
                    projectName = pname
                }
            }).ToString();
            ***/
            //劳务通

            try
            {
                string data = JObject.FromObject(new { projectName = pname }).ToString();
                string task = await CommonUtil.GetResponseAsync(CommonUtil.GetConfigValue("postCountURL"), data);
                if (null == task)
                    return;
                JObject jj = JObject.Parse(task);
                if (null == jj)
                    return;
                string v = jj["userCount"].ToObject<string>();

                Console.WriteLine(task);
                this.label6.Text = v;
                this.label6.Refresh();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message+ " method:GetcountFromURL");
            }
         
        }
        public async void UpadteAllAtUsers(string pname)
        {
            string data = JObject.FromObject(new
            {
                projectName = pname
            }).ToString();
            try
            {
                string task = await CommonUtil.GetResponseAsync(CommonUtil.GetConfigValue("getRecordInfoByProject"), data);
                Console.WriteLine(task);
                JObject jj = JObject.Parse(task);
                if (null == jj)
                    return;
                if (null == jj["count"])
                    return;
                string v = jj["count"].ToObject<string>();
                this.label2.Text = v;
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
                this.label2.Text = "0";
            }
            this.label2.Refresh();
        }
        /// <summary>
        /// 获取项目下所有工人信息
        /// </summary>
        /// <param name="pname"></param>
        private async void GetAllUsers(string pname)
        {
            string postURL = ConfigurationManager.AppSettings["postlistURL"].ToString();
            /***  实名制版本写法
            string data = JObject.FromObject(new
            {
                userInfo = new
                {
                    projectName = pname
                }
            }).ToString();
            ***/
            //劳务通版本写法
            string data = JObject.FromObject(new { projectName=pname}).ToString();
            try
            {
                string task = await CommonUtil.GetResponseAsync(postURL, data);
                if (String.IsNullOrEmpty(task))
                    return;
                JObject jj = JObject.Parse(task);
                IList<JToken> results = jj["items"].Children().ToList();
                IList<UserInfo> searchResults = new List<UserInfo>();
                foreach (JToken result in results)
                {
                    UserInfo searchResult = result.ToObject<UserInfo>();
                    searchResults.Add(searchResult);
                }
                foreach (UserInfo u in searchResults)
                {
                    DataTable dt = SQLiteDBHelper.ExecuteDataTable(string.Format("select * from worker where identityId = '{0}' and groupname = '{1}';", u.userId, pname));
                    DataRow row = null;
                    if (dt.Rows.Count > 0)
                        row = dt.Rows[0];
                    string sql = string.Empty;
                    if (null == row)
                    {
                        sql = string.Format("insert into worker(identityId,username,contact,job,groupname,addtime,checkinState,checkinTime,identityPhoto,classNo)values('{0}','{1}','{2}','{3}','{4}','{5}',{6},'{7}','{8}','{9}')",
                            u.userId,
                            u.userName,
                            u.mobile,
                            u.workKindName,
                            u.projectName,
                            u.createTime,
                            0,
                            "1990-12-12",
                            u.photo,
                            u.classNo
                            );
                       
                    }
                    else
                    {
                        sql = string.Format("update worker set job = '{0}',classNo = '{1}' where identityId = '{2}' and groupname ='{3}'",
                                u.workKindName,
                                u.classNo,
                                u.userId,
                                u.projectName
                            );
                        
                    }
                    if (SQLiteDBHelper.ExecuteNonQuery(sql) > 0)
                        Console.WriteLine(sql);
                }
                CommonUtil.DebugConsole("用户总数为：" + searchResults.Count);
            }
            catch (Exception ex)
            {
                Console.WriteLine("***" + ex.Message);
            }

        }

        private void Home_Load(object sender, EventArgs e)
        {
            timer1_Tick(null, null);
            timer2_Tick(null, null);
        }

        /***
         * 
         * 开启串口通信
         * 
         */
        private void startSerialPort()
        {
            try
            {
                string sql = "select port,baud_rate,odd_even_valid from setting_info";
                DataTable table = SQLiteDBHelper.ExecuteDataTable(sql);
                if (table.Rows.Count < 0)
                {
                    MessageBox.Show("请先设置串口参数", "提示", MessageBoxButtons.OKCancel, MessageBoxIcon.Error);
                    return;
                }
                DataRow row = table.Rows[0];
                this.serialPort.PortName = row["port"].ToString();
                this.serialPort.BaudRate = Convert.ToInt32(row["baud_rate"].ToString());
                this.serialPort.Parity = Parity.None;
                this.serialPort.StopBits = StopBits.One;
                this.serialPort.DataReceived += new SerialDataReceivedEventHandler(Sp_DataReceived);
                interfaceUpdataHandle = new HandleInterfaceUpdataDelegate(UpdateSerialPortData);//实例化委托对象
                this.serialPort.ReceivedBytesThreshold = 1;
                try
                {
                    if (!this.serialPort.IsOpen)
                    {
                        this.serialPort.Open();
                        MessageBox.Show("端口" + serialPort + "打开成功！");
                        btnSwitchPort.Text = "关闭串口";
                    }
                }
                catch
                {
                    MessageBox.Show("端口" + serialPort + "打开失败！");
                }
            }
            catch
            {
                return;
            }
        }

        /**
         * 开启多串口的通信串口
         */
        private void startSerialPort2()
        {
            try
            {
                if (serialPortsList.Count > 0)
                {
                    serialPortsList.Clear();
                }
                string sql = "select port,baud_rate,odd_even_valid,tag from setting_info";
                DataTable table = SQLiteDBHelper.ExecuteDataTable(sql);
                int count = table.Rows.Count;
                if (count <= 0)
                {
                    MessageBox.Show("请先设置串口参数", "提示", MessageBoxButtons.OKCancel, MessageBoxIcon.Error);
                    return;
                }
                for (int i = 0; i < count; i++)
                {
                    DataRow row = table.Rows[i];
                    SerialPort port = new SerialPort();
                    SerialDataReceiveHandler handler = new SerialDataReceiveHandler(port, this, Convert.ToInt32(row["tag"].ToString()), this.lstBoxWorker, this.images, this.inuser,
                    this.outuser,
                    this.nowinname,
                    this.nowoutname,
                    this.nowindate,
                    this.nowoutdate,
                    this.nowingroup,
                    this.nowoutgroup,
                    this.nowintime,
                    this.nowouttime,
                    this.nowinjob,
                    this.nowoutjob);

                    port.PortName = row["port"].ToString();
                    port.BaudRate = Convert.ToInt32(row["baud_rate"].ToString());
                    port.Parity = Parity.None;
                    port.StopBits = StopBits.One;
                    port.DataReceived += new SerialDataReceivedEventHandler(handler.Sp_DataReceived);
                    port.ReceivedBytesThreshold = 1;
                    //Console.WriteLine("波特率：{0}", port.BaudRate);
                    serialPortsList.Add(port);
                    try
                    {
                        if (!port.IsOpen)
                        {
                            port.Open();
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("端口" + port + "打开失败！" + ex.Message);
                        CloseAllPorts();
                        return;
                    }
                }
                this.timer3.Enabled = true;
                MessageBox.Show("端口打开成功");
                btnSwitchPort.Text = "关闭串口";
            }
            catch (Exception)
            {
                throw;
            }
        }

        private void CloseAllPorts()
        {
            foreach (SerialPort port in serialPortsList)
            {
                try
                {
                    if (port.IsOpen)
                    {
                        port.DiscardInBuffer();
                        port.Close();
                    }
                }
                catch (Exception)
                {
                    throw;
                }
            }
        }

        /**
         * 串口接收数据处理事件
         * 
         */
        public void Sp_DataReceived(object sender, System.IO.Ports.SerialDataReceivedEventArgs e)
        {
            try
            {
                int i = this.serialPort.BytesToRead;
                if (i > 0)
                {
                    try
                    {
                        string message = this.serialPort.ReadExisting();
                        this.Invoke(interfaceUpdataHandle, message);

                    }
                    catch (Exception exception)
                    {
                        MessageBox.Show(exception.Message);
                    }
                }
            }
            catch
            {
                throw;
            }
        }

        /***
         * 
         * dataReceive处理后的下一步委托事件处理方法
         */
        public void UpdateSerialPortData(String message)
        {
            if (message.IndexOf("\r\n") != -1)
            {
                message = message.Replace("\r\n", "");
            }
            if (message.Substring(0, 3).IndexOf("00") != -1)
            {
                int len = message.Length;
                message = message.Substring(2, len - 2);
            }
            MessageBox.Show(message);
        }

        /***
         * 
         * "设置"按钮点击事件
         */
        private void btnSetting_Click(object sender, EventArgs e)
        {
            /*    SettingForm settingForm=new SettingForm();
                settingForm.ShowDialog();*/
            MultiSettingForm settingForm = new MultiSettingForm();
            settingForm.ShowDialog();
        }

        /***
         * 窗体关闭时候事件处理
         */
        private void Home_FormClosing(object sender, FormClosingEventArgs e)
        {
            CloseAllPorts();
            Application.Exit();
        }

        /***
         * 开启/关闭串口事件
         */
        private void SwitchPortClick(object sender, EventArgs e)
        {
            try
            {
                foreach (SerialPort port in serialPortsList)
                {
                    if (port.IsOpen)
                    {
                        CloseAllPorts();
                        btnSwitchPort.Text = "开启串口";
                        return;
                    }
                }
            }
            catch
            {
                MessageBox.Show("端口关闭异常");
                return;
            }
            startSerialPort2();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            this.labelTime.Text = DateTime.Now.ToString("yyyy年 MM月dd日 dddd");
        }
        //工人考勤轮播绘图事件
        private void lstBoxWorker_DrawItem(object sender, DrawItemEventArgs e)
        {

            Brush myBrush = new SolidBrush(Color.FromArgb(32, 32, 48));
            e.Graphics.FillRectangle(myBrush, e.Bounds);
            e.DrawFocusRectangle();//焦点框 

            if (lstBoxWorker.Items.Count == 0)
                return;
            //Console.WriteLine(string.Format("index = {0}", e.Index));
            LoopUser u = new LoopUser();
            u = (LoopUser)lstBoxWorker.Items[e.Index];
            Image image = new Bitmap(new MemoryStream(Convert.FromBase64String(u.photo)));
            Graphics g = e.Graphics;
            Rectangle bounds = e.Bounds;
            Rectangle imageRect = new Rectangle(
                bounds.X,
                bounds.Y + 5,
                bounds.Height / 16 * 14,
                bounds.Height);
            Rectangle textRect = new Rectangle(
                imageRect.Right + 15,
                bounds.Y + 5,
                bounds.Width - imageRect.Right,
                bounds.Height);

            if (image != null)
            {
                g.DrawImage(
                    image,
                    imageRect,
                    0,
                    0,
                    image.Width,
                    image.Height,
                    GraphicsUnit.Pixel);
            }

            //文本 
            StringFormat strFormat = new StringFormat();

            strFormat.LineAlignment = StringAlignment.Center;
            e.Graphics.DrawString(u.info, e.Font, new SolidBrush(e.ForeColor), textRect, strFormat);
            this.lstBoxWorker.ScrollAlwaysVisible = false;


        }


        /********   视频监控部分    **************************************************/
        /// <summary>
        /// 初始化视频
        /// </summary>
        private void InitCamera()
        {
            m_bInitSDK = CHCNetSDK.NET_DVR_Init();
            if (m_bInitSDK == false)
            {
                MessageBox.Show("初始化监控失败！");
                return;
            }
            else
            {
                //保存SDK日志 To save the SDK log
                CHCNetSDK.NET_DVR_SetLogToFile(3, "C:\\SdkLog\\", true);

                for (int i = 0; i < 64; i++)
                {
                    iIPDevID[i] = -1;
                    iChannelNum[i] = -1;
                }
            }
        }


        //开启视频监控
        private void OpenCameraWatch()
        {
            string CameraSupply = ConfigurationManager.AppSettings["CameraSupply"].ToString();
            //判断视频厂商，暂时先用海康的接口，如果不是海康则不启用视频
            if (CameraSupply != "海康")
            {
                return;
            }
            InitCamera();
            string ipAddress = CommonUtil.GetConfigValue("CameraIp");
            string cameraPort = CommonUtil.GetConfigValue("CameraPort");
            string cameraUser = CommonUtil.GetConfigValue("CameraUser");
            string cameraPwd = CommonUtil.GetConfigValue("CameraPwd");
            //登录设备 Login the device
            m_lUserID = CHCNetSDK.NET_DVR_Login_V30(ipAddress, Int16.Parse(cameraPort), cameraUser, cameraPwd, ref DeviceInfo);
            if (m_lUserID < 0)
            {
                iLastErr = CHCNetSDK.NET_DVR_GetLastError();
                MessageBox.Show("监控连接出错" + iLastErr);
                return;
            }
            else
            {
                dwAChanTotalNum = (uint)DeviceInfo.byChanNum;
                dwDChanTotalNum = (uint)DeviceInfo.byIPChanNum + 256 * (uint)DeviceInfo.byHighDChanNum;
                if (dwDChanTotalNum > 0)
                {
                    InfoIPChannel();
                    PreviewAction();
                }
                else
                {
                    for (i = 0; i < dwAChanTotalNum; i++)
                    {
                        ListAnalogChannel(i + 1, 1);
                        iChannelNum[i] = i + (int)DeviceInfo.byStartChan;
                    }
                }
            }
        }

        public void InfoIPChannel()
        {
            uint dwSize = (uint)Marshal.SizeOf(m_struIpParaCfgV40);

            IntPtr ptrIpParaCfgV40 = Marshal.AllocHGlobal((Int32)dwSize);
            Marshal.StructureToPtr(m_struIpParaCfgV40, ptrIpParaCfgV40, false);

            uint dwReturn = 0;
            int iGroupNo = 0;  //该Demo仅获取第一组64个通道，如果设备IP通道大于64路，需要按组号0~i多次调用NET_DVR_GET_IPPARACFG_V40获取

            if (!CHCNetSDK.NET_DVR_GetDVRConfig(m_lUserID, CHCNetSDK.NET_DVR_GET_IPPARACFG_V40, iGroupNo, ptrIpParaCfgV40, dwSize, ref dwReturn))
            {
                iLastErr = CHCNetSDK.NET_DVR_GetLastError();
                str = "NET_DVR_GET_IPPARACFG_V40 failed, error code= " + iLastErr;
                //获取IP资源配置信息失败，输出错误号 Failed to get configuration of IP channels and output the error code
                MessageBox.Show(str);
            }
            else
            {
                //MessageBox.Show("视频开启成功!", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                //Console.WriteLine("视频开启成功!");
                m_struIpParaCfgV40 = (CHCNetSDK.NET_DVR_IPPARACFG_V40)Marshal.PtrToStructure(ptrIpParaCfgV40, typeof(CHCNetSDK.NET_DVR_IPPARACFG_V40));

                for (i = 0; i < dwAChanTotalNum; i++)
                {
                    ListAnalogChannel(i + 1, m_struIpParaCfgV40.byAnalogChanEnable[i]);
                    iChannelNum[i] = i + (int)DeviceInfo.byStartChan;
                }

                byte byStreamType = 0;
                uint iDChanNum = 64;

                if (dwDChanTotalNum < 64)
                {
                    iDChanNum = dwDChanTotalNum; //如果设备IP通道小于64路，按实际路数获取
                }

                for (i = 0; i < iDChanNum; i++)
                {
                    iChannelNum[i + dwAChanTotalNum] = i + (int)m_struIpParaCfgV40.dwStartDChan;
                    byStreamType = m_struIpParaCfgV40.struStreamMode[i].byGetStreamType;

                    dwSize = (uint)Marshal.SizeOf(m_struIpParaCfgV40.struStreamMode[i].uGetStream);
                    switch (byStreamType)
                    {
                        //目前NVR仅支持直接从设备取流 NVR supports only the mode: get stream from device directly
                        case 0:
                            IntPtr ptrChanInfo = Marshal.AllocHGlobal((Int32)dwSize);
                            Marshal.StructureToPtr(m_struIpParaCfgV40.struStreamMode[i].uGetStream, ptrChanInfo, false);
                            m_struChanInfo = (CHCNetSDK.NET_DVR_IPCHANINFO)Marshal.PtrToStructure(ptrChanInfo, typeof(CHCNetSDK.NET_DVR_IPCHANINFO));

                            //列出IP通道 List the IP channel
                            //ListIPChannel(i + 1, m_struChanInfo.byEnable, m_struChanInfo.byIPID);
                            iIPDevID[i] = m_struChanInfo.byIPID + m_struChanInfo.byIPIDHigh * 256 - iGroupNo * 64 - 1;

                            Marshal.FreeHGlobal(ptrChanInfo);
                            break;
                        case 6:
                            IntPtr ptrChanInfoV40 = Marshal.AllocHGlobal((Int32)dwSize);
                            Marshal.StructureToPtr(m_struIpParaCfgV40.struStreamMode[i].uGetStream, ptrChanInfoV40, false);
                            m_struChanInfoV40 = (CHCNetSDK.NET_DVR_IPCHANINFO_V40)Marshal.PtrToStructure(ptrChanInfoV40, typeof(CHCNetSDK.NET_DVR_IPCHANINFO_V40));

                            //列出IP通道 List the IP channel
                            // ListIPChannel(i + 1, m_struChanInfoV40.byEnable, m_struChanInfoV40.wIPID);
                            iIPDevID[i] = m_struChanInfoV40.wIPID - iGroupNo * 64 - 1;

                            Marshal.FreeHGlobal(ptrChanInfoV40);
                            break;
                        default:
                            break;
                    }
                }
            }
            Marshal.FreeHGlobal(ptrIpParaCfgV40);

        }

        public void ListAnalogChannel(Int32 iChanNo, byte byEnable)
        {
            str1 = String.Format("Camera {0}", iChanNo);
            m_lTree++;

            if (byEnable == 0)
            {
                str2 = "Disabled"; //通道已被禁用 This channel has been disabled               
            }
            else
            {
                str2 = "Enabled"; //通道处于启用状态 This channel has been enabled
            }
        }

        public void PreviewAction()
        {
            Preview(this.pictureBox1, 0);
        }

        private void Preview(PictureBox RealPlayWnd, int selectIndex)
        {
            CHCNetSDK.NET_DVR_PREVIEWINFO lpPreviewInfo = new CHCNetSDK.NET_DVR_PREVIEWINFO();
            lpPreviewInfo.hPlayWnd = RealPlayWnd.Handle;//预览窗口 live view window
            lpPreviewInfo.lChannel = iChannelNum[selectIndex];//预览的设备通道 the device channel number
            lpPreviewInfo.dwStreamType = 0;//码流类型：0-主码流，1-子码流，2-码流3，3-码流4，以此类推
            lpPreviewInfo.dwLinkMode = 0;//连接方式：0- TCP方式，1- UDP方式，2- 多播方式，3- RTP方式，4-RTP/RTSP，5-RSTP/HTTP 
            lpPreviewInfo.bBlocked = true; //0- 非阻塞取流，1- 阻塞取流
            lpPreviewInfo.dwDisplayBufNum = 15; //播放库显示缓冲区最大帧数
            IntPtr pUser = IntPtr.Zero;//用户数据 user data 
                                       //打开预览 Start live view 
            m_lRealHandle = CHCNetSDK.NET_DVR_RealPlay_V40(m_lUserID, ref lpPreviewInfo, null/*RealData*/, pUser);
            RealPlayWnd.Invalidate();
        }

        /// <summary>
        /// 定时更新在场人数
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer2_Tick(object sender, EventArgs e)
        {
            if (IsHandleCreated)
            {
                Invoke(new Action(() =>
                {
                    this.label4.Text = workerService.UpdateWorkerNumber(pname, 1);
                }));
            }
        }
        /// <summary>
        /// 定时线上查询更新各工种考勤人数
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer3_Tick(object sender, EventArgs e)
        {
            if (CommonUtil.GetConfigValue("mode") == "local")
                UpdateWorkerJobCount();
            if (CommonUtil.GetConfigValue("mode") == "localc")
                UpdateWorkerClassCountOnlineAsync();
            if (CommonUtil.GetConfigValue("mode") == "localj")
                UpdateWorkerJobCountOnlineAsync();

        }
        /// <summary>
        /// 定时更新考勤人数
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer4_Tick(object sender, EventArgs e)
        {
            UpadteAllAtUsers(ConfigurationManager.AppSettings["prorealname"].ToString());
        }


        protected override void OnSizeChanged(EventArgs e)
        {
            base.OnSizeChanged(e);
            if (ControlsInfo.Count > 0)//如果字典中有数据，即窗体改变
            {
                ControlsChaneInit(this.Controls[0]);//表示pannel控件
                ControlsChange(this.Controls[0]);
            }
        }
        /// <summary>
        /// 单击标题获取项目下所有人员信息至数据库
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void labproname_Click(object sender, EventArgs e)
        {
            //GetAllUsers(ConfigurationManager.AppSettings["prorealname"].ToString());
        }

        private void label5_Click(object sender, EventArgs e)
        {
            try
            {
                foreach (SerialPort port in serialPortsList)
                {
                    if (port.IsOpen)
                    {
                        CloseAllPorts();
                        btnSwitchPort.Text = "开启串口";
                        return;
                    }
                }

            }
            catch
            {
                MessageBox.Show("端口关闭异常");
                return;
            }
            startSerialPort2();
        }

        private void label1_Click(object sender, EventArgs e)
        {
            MultiSettingForm settingForm = new MultiSettingForm();
            settingForm.ShowDialog();
        }

        private void label3_Click(object sender, EventArgs e)
        {
            LedInfo info = new LedInfo();
            string sql = "select * from led_info";
            DataTable table = SQLiteDBHelper.ExecuteDataTable(sql);
            DataRow row = null;
            if (table.Rows.Count > 0)
            {
                row = table.Rows[0];
                info.Id = Int32.Parse(row["id"].ToString());
                info.ProjectName = row["project_name"].ToString();
                info.LedIp = row["led_ip"].ToString();
                info.Width = Int32.Parse(row["width"].ToString());
                info.Height = Int32.Parse(row["height"].ToString());
                LedModify ledModify = new LedModify(info);
                //   LedModify ledModify = new LedModify(info);
                ledModify.ShowDialog();
            }
            else
            {
                LedModify ledModify = new LedModify();
                //   LedModify ledModify = new LedModify(info);
                ledModify.ShowDialog();
            }
        }
        private void timer5_Tick(object sender, EventArgs e)
        {
            if (DateTime.Now.Hour == 12 && DateTime.Now.Minute == 1)
                GetcountFromURL(ConfigurationManager.AppSettings["prorealname"].ToString());
        }

        private void timer6_Tick(object sender, EventArgs e)
        {
            if (DateTime.Now.Hour == 23 && DateTime.Now.Minute == 59)
                this.lstBoxWorker.Items.Clear();
        }

        private void labelTime_Click(object sender, EventArgs e)
        {
            
        }
        private void label4_ClickAsync(object sender, EventArgs e)
        {
            workerService.LedWorkGroupControl(workerService.GetLEDInfo(), "", 3);
        }

        private void timer7_Tick(object sender, EventArgs e)
        {
            if (CommonUtil.GetConfigValue("mode") == "local")
                workerService.LedWorkGroupControl(workerService.GetLEDInfo(), "", 3);
        }

        private void GetWeatherAsync()
        {
            string strURL = "https://restapi.amap.com/v3/weather/weatherInfo?key=0a73c5c6be50f0a6dcebcaf3f7eaa2e0&extensions=all&city=" + CommonUtil.GetConfigValue("city");
            try
            {
                System.Net.HttpWebRequest request;
                // 创建一个HTTP请求
                request = (System.Net.HttpWebRequest)WebRequest.Create(strURL);

                System.Net.HttpWebResponse response;
                response = (System.Net.HttpWebResponse)request.GetResponse();
                System.IO.StreamReader myreader = new System.IO.StreamReader(response.GetResponseStream(), Encoding.UTF8);
                string responseText = myreader.ReadToEnd();


                //Console.WriteLine("天气返回：" + responseText);
                JObject googleSearch = JObject.Parse(responseText);

                // get JSON result objects into a list
                IList<JToken> results = googleSearch["forecasts"][0]["casts"].Children().ToList();

                // serialize JSON results into .NET objects
                IList<Weather> searchResults = new List<Weather>();
                foreach (JToken result in results)
                {
                    // JToken.ToObject is a helper method that uses JsonSerializer internally
                    Weather searchResult = result.ToObject<Weather>();
                    searchResults.Add(searchResult);
                }

                this.wpicture.BackgroundImage = Image.FromFile(Application.StartupPath + @"\Resources\3d_60\" + searchResults[0].dayweather + ".png");
                this.ttemp.Text = string.Format("{0}℃ ~ {1}℃", searchResults[0].daytemp, searchResults[0].nighttemp);
                if (!searchResults[0].dayweather.Equals(searchResults[0].nightweather))//避免多云转多云这种尴尬的情况
                    this.tw.Text = string.Format("{0}转{1}", searchResults[0].dayweather, searchResults[0].nightweather);
                else
                    this.tw.Text = string.Format("{0}", searchResults[0].dayweather);
                this.twind.Text = string.Format("{0}风 {1}级", searchResults[0].daywind, searchResults[0].daypower);
                this.nweather.Text = string.Format("{0}℃ ~ {1}℃ {2}", searchResults[1].daytemp, searchResults[1].nighttemp, searchResults[0].dayweather);
            }
            catch (Exception ex) { Console.WriteLine(ex.Message); }
        }

        private void timer9_Tick(object sender, EventArgs e)
        {
            if (DateTime.Now.Hour == 10 && DateTime.Now.Minute == 30)
            {
                Console.WriteLine("天气更新");
                this.GetWeatherAsync();
            }
        }
        //DrawBorder(Graphics graphics, Rectangle bounds, Color color, ButtonBorderStyle style);
        private void tlp_Paint(object sender, PaintEventArgs e)
        {
            ControlPaint.DrawBorder(
                 e.Graphics,
                 tlp.ClientRectangle,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid
                 );
        }

        private void panel9_Paint(object sender, PaintEventArgs e)
        {
            ControlPaint.DrawBorder(
                 e.Graphics,
                 panel9.ClientRectangle,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid
                 );
        }

        private void tableLayoutPanel1_Paint(object sender, PaintEventArgs e)
        {
            ControlPaint.DrawBorder(
                 e.Graphics,
                 tableLayoutPanel1.ClientRectangle,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid
                 );
        }

        private void panel6_Paint(object sender, PaintEventArgs e)
        {
            ControlPaint.DrawBorder(
               e.Graphics,
                panel8.ClientRectangle,
                Color.Gray,
                0,
                ButtonBorderStyle.Solid,
                Color.Gray,
                0,
                ButtonBorderStyle.Solid,
                Color.Gray,
                1,
                ButtonBorderStyle.Solid,
                Color.Gray,
                0,
                ButtonBorderStyle.Solid
                );
        }

        private void panel8_Paint(object sender, PaintEventArgs e)
        {
            ControlPaint.DrawBorder(
                e.Graphics,
                panel8.ClientRectangle,
                Color.Gray,
                0,
                ButtonBorderStyle.Solid,
                Color.Gray,
                0,
                ButtonBorderStyle.Solid,
                Color.Gray,
                1,
                ButtonBorderStyle.Solid,
                Color.Gray,
                0,
                ButtonBorderStyle.Solid
                );
        }

        private void panel4_Paint(object sender, PaintEventArgs e)
        {
            ControlPaint.DrawBorder(
                 e.Graphics,
                 panel4.ClientRectangle,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid,
                 BorderColor,
                 3,
                 ButtonBorderStyle.Solid
                 );
        }

        private void timer8_Tick(object sender, EventArgs e)
        {
            this.timenow.Text = DateTime.Now.ToString("T");
        }

        private void slogen_ClickAsync(object sender, EventArgs e)
        {
            using (var client = new HttpClient())
            {
                /*** 劳务通版本不需要登录
                client.DefaultRequestHeaders.Add("Referer", ConfigurationManager.AppSettings["referer"].ToString());
                List<KeyValuePair<String, String>> paramList = new List<KeyValuePair<String, String>>();
                paramList.Add(new KeyValuePair<string, string>("userName", ConfigurationManager.AppSettings["user"].ToString()));
                paramList.Add(new KeyValuePair<string, string>("password", ConfigurationManager.AppSettings["pwd"].ToString()));
                HttpResponseMessage response = new HttpResponseMessage();
                Task<HttpResponseMessage> Taskresponse = client.PostAsync(new Uri(ConfigurationManager.AppSettings["loginURL"].ToString()), new FormUrlEncodedContent(paramList));
                string res = Taskresponse.Result.Content.ReadAsStringAsync().Result;
                Token t = JsonConvert.DeserializeObject<Token>(res);
                ***/

                string postURL = ConfigurationManager.AppSettings["postCountURL"].ToString();
                /*** 实名制版本写法
                string data = JObject.FromObject(new
                {
                    userInfo = new
                    {
                        projectName = ConfigurationManager.AppSettings["prorealname"].ToString()
                    }
                }).ToString();
                ***/
                //劳务通版本写法
                string data = JObject.FromObject(new { projectName = ConfigurationManager.AppSettings["prorealname"].ToString() }).ToString();
                HttpContent hc = new StringContent(data);

                hc.Headers.ContentType = new MediaTypeHeaderValue("application/json");
              //  hc.Headers.Add("token", t.token);

                HttpResponseMessage content = client.PostAsync(postURL, hc).Result;
                Task<string> Tasktask = content.Content.ReadAsStringAsync();

                try
                {
                    JObject jj = JObject.Parse(Tasktask.Result);

                    //Console.WriteLine("项目人数：" + jj["record"].ToString());
                    //updateAsyncData("GetProjectCountInfoAsync", "项目人数：" + jj["record"].ToString());
                }
                catch (Exception ex)
                {
                    //Console.WriteLine("项目人数：0");
                    Console.WriteLine("数据解析错误" + ex.Message);
                }
            }
        }

        private void autoUpdateOnlineData_Tick(object sender, EventArgs e)
        {
            if (worker.IsBusy)
                return;
            worker.RunWorkerAsync();
        }

        private void foundation_Click(object sender, EventArgs e)
        {
            workerService.GetClassCountLocal();
        }
    }

}
