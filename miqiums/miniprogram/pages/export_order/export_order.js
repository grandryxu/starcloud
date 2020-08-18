// pages/export_order/export_order.js
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
    }, ],
    cbox_m_names:[]

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const db = wx.cloud.database()
    let that = this
    db.collection('export').orderBy('time','desc').limit(100)
    .get({
      success:function(res){
        that.setData({
          listData: res.data
        })

      }
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  checkboxChange(e){
    console.log(e.detail.value)
    this.setData({
      cbox_m_names:e.detail.value
    })

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

    inputQueryDay(e) {
      this.setData({
        [`queryObj.day`]: e.detail.value
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
      }).limit(100)
      .get({
        success: function (res) {
          that.setData({
            listData: res.data
          })

        }
      })

  },
  OneKeyImport(){
    let selectItems=[]
    let len1 = this.data.listData.length
    let len2 = this.data.cbox_m_names.length
    let that = this

    for(let i=0;i<len1;i++){
      for(let j=0;j<len2;j++){
        if(this.data.listData[i].name==this.data.cbox_m_names[j]){
          selectItems.push(this.data.listData[i])
        }
      }
    }
    // console.log(selectItems)
    wx.showLoading({
      title: '入库中',
      mask:'true'
    })
     //获取物料库所有物料
     const db = wx.cloud.database()
     db.collection('material').get({
       success:function(res){
        // console.log(res.data)
         let materials = res.data
         let len3 = materials.length
         let len4 = selectItems.length
         let tochangeItems=[]  //保存即将改变数量的物料,同时存储出库表id，以便更新出库表信息
         for(let m=0;m<len3;m++){
          for(let k=0;k<len4;k++){
            if(materials[m].material_name==selectItems[k].name){
              tochangeItems.push({
                _id:materials[m]._id,
                num:selectItems[k].num,
                export_id:selectItems[k]._id
              })
            }
          }
        }

        console.log(tochangeItems)
        let count=0 //记录云函数更新成功条数
        //调用云函数更新物料库
        for(let q=0;q<tochangeItems.length;q++){
          console.log(tochangeItems[q]._id+'-------'+Number(tochangeItems[q].num))
          wx.cloud.callFunction({
            name:'onekeyimport',
            data:{
             _id: tochangeItems[q]._id,
             import_num: Number(tochangeItems[q].num)
            },
            success:function(response){
              //更新物料库成功一次，同时更新出库表信息，将is_export设为true,is_export表示当前记录是否被一键入库
              wx.cloud.callFunction({
                name:'updateExportOrder',
                data:{
                  _id:tochangeItems[q].export_id
                }
              })
                count++
                if(count==tochangeItems.length){
                  wx.hideLoading()
                  wx.showToast({
                    title: '一键入库成功',
                    icon:'success',
                    duration:1000
                  })
                 
                  //重新加载
                  setTimeout(() => {
                    that.onLoad()
                  }, 1200);
                
                  

                }
            }
          })

        }
    


       }
     })


  

  }
})