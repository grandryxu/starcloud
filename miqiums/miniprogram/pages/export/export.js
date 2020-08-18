Page({
  data: {
    searchValue: '',
    queryObj: {
      material: '',
      remark: '',
      month: '',
      day: ''
    },
    listData: [{
      "code": "01",
      "name": "text1",
      "num": 20,
      "add_time":"",
      "remark":"新增记录"
    }, ]

  },

  onLoad:function(){
    const db = wx.cloud.database()
    let that = this
    db.collection('export').orderBy('time','desc')
    .get({
      success:function(res){
        that.setData({
          listData: res.data
        })

      }
    })

  },

  OnRefresh:function(){
    this.onLoad()
  },

  //查询输入框部分
  inputQueryMaterial(e) {
    this.setData({
      [`queryObj.material`]: e.detail.value
    })
    console.log(this.data.queryObj)
  },

  inputQueryRemark(e) {
    this.setData({
      [`queryObj.remark`]: e.detail.value
    })
    console.log(this.data.queryObj)

  },

  inputQueryMonth(e) {
    this.setData({
      [`queryObj.month`]: e.detail.value
    })
    console.log(this.data.queryObj)
  },

  GetExportInfo(e) {
    const db = wx.cloud.database()
    const _ = db.command
    let that = this
    db.collection('export').orderBy('time', 'desc')
      .where({
        name: {
          $regex: '.*' + that.data.queryObj.material
        },
        remark: {
          $regex: '.*' + that.data.queryObj.remark
        },
        add_time: {
          $regex: '.*' + that.data.queryObj.month + "/" + that.data.queryObj.day + '.*'
        }
      })
      .get({
        success: function (res) {
          that.setData({
            listData: res.data
          })

        }
      })

  },

  inputQueryDay(e) {
    this.setData({
      [`queryObj.day`]: e.detail.value
    })
    console.log(this.data.queryObj)
  },

  GotoExportAdd:function(){
    wx.navigateTo({
      url: '../export_add/export_add',
    })

  },

})