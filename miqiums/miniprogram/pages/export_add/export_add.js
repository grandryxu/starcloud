Page({
  data: {
    material_name:'',
    export_num:0,
    remark:'',
    index: 0,
    array: [{
      _id:'111',
      name:'KT猫'
    }],
    doc_id:''

  },

  onLoad: function() {
    const db = wx.cloud.database()
    let that = this
    db.collection('material').get({
      success: function (res) {
        let arr = []
        for(var index in res.data){
         // arr.push(res.data[index].material_name)
         arr.push({
           _id: res.data[index]._id,
           name: res.data[index].material_name
         })
        }
        that.setData({
          array: arr,
          material_name: res.data[0].material_name,
          doc_id: res.data[0]._id
        })
       // console.log(that.data.array)
      }
    })

  },

  FormSubmit(e) {
    const db = wx.cloud.database()
    let that = this;
    wx.showLoading({
      title: '保存中',
      mask: 'true'
    })
    db.collection('export').add({
     data:{
       name: that.data.material_name,
       num: that.data.export_num,
       remark:that.data.remark,
       add_time: (new Date().getMonth() + 1).toString() + "/" + new Date().getDate().toString(),
       is_export:false,
       time:new Date()
     },
     success:function(res){
      //更新物料数目
      const _=db.command
      console.log(that.data.doc_id)

    /**   本地方法存在权限限制  db.collection('material').doc(that.data.doc_id).update({
        data:{
          material_num: _.inc(that.data.export_num*(-1))
        },
        success:function(response){
          wx.showToast({
            title: '出库成功',
            icon: 'success',
            duration: 1000
          })
          setTimeout(function () {
            wx.navigateBack()
          }, 1500)

        }
      })  **/

      //云函数代替本地方法
      wx.cloud.callFunction({
        name:'updateExport',
        data:{
          _id: that.data.doc_id,
          export_num: that.data.export_num
        },
        success:function(response){
          wx.hideLoading()
          wx.showToast({
            title: '出库成功',
            icon: 'success',
            duration: 1000
          })
          setTimeout(function () {
            wx.navigateBack()
          }, 1500)
        }
      })

     }

    })  

  },

  InputEditNum:function(e){
    let value = e.detail.value
    this.setData({
      export_num: value
    })
  },

  InputEditRemark: function (e) {
    let value = e.detail.value
    this.setData({
      remark: value
    })
  },

  GoBack: function() {
    wx.navigateBack()
  },

  bindPickerChange: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value,
      material_name: this.data.array[e.detail.value].name,
      doc_id:this.data.array[e.detail.value]._id
    })
  },

})