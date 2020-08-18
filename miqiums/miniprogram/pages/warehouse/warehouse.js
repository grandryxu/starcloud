Page({
  data: {
    searchValue: '',
    listData: [{
      "code": "01",
      "material_name": "text1",
      "material_num": 20,
      "unit": "type1",
      "fileId":''
    }, ]

  },

  onLoad: function() {
    const db = wx.cloud.database()
    let that = this
    db.collection('material').orderBy('time','desc')
    .get({
      success: function(res) {
        that.setData({
          listData:res.data
        })
        console.log(res.data[0].material_name)
      }
    })

  },
  inputEdit: function(e) {
    let value = e.detail.value
    this.setData({
      searchValue: value
    })
  },

  //预览图片
  previewImg(e){
    var index = e.currentTarget.dataset.index;
    var file_id=this.data.listData[index].fileId
    wx.previewImage({
      //当前显示图片
      current: file_id,
      urls:[file_id]
    })

     
  },
  //获取数据库物品数量
  GetMaterialInfo: function() {
    const db = wx.cloud.database()
    let that = this
    db.collection('material').where({
      material_name: this.data.searchValue
    }).get({
      success: function(res) {
        that.setData({
          listData: res.data
        })
        console.log(res.data[0].material_name)
      }
    })

  },


})