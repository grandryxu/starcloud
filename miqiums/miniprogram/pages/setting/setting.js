Page({

  data: {
    material_name: '',
    material_num: 0,
    unit: '',
    imgs: [],
    cloudPath:'',
    fileId:'',
    items: [{
        value: 'add',
        name: '新增'
      },
      {
        value: 'update',
        name: '修改'
      },
      {
        value: 'delete',
        name: '删除'
      }
    ],
    radio: 'add',
    doc_id: ''

  },


  RadioChange(e) {
    console.log(e.detail.value);
    this.setData({
      radio: e.detail.value
    })

  },


  //云函数调用
  CloudCall() {
    wx.cloud.callFunction({
      // 云函数名称
      name: 'add',
      // 传给云函数的参数
      data: {
        a: 1,
        b: 2,
      },
      success: function(res) {
        console.log(res.result)
        console.log(res.result.sum) // 3
      },
      fail: console.error
    })
  },

  InputEditMaterial(e) {
    this.setData({
      material_name: e.detail.value
    })

  },

  InputEditNum(e) {
    this.setData({
      material_num: Number(e.detail.value)
    })
  },

  InputEditUnit(e) {
    this.setData({
      unit: e.detail.value
    })
  },

  GoBack: function() {
    wx.navigateBack()
  },

  FormSubmit(e) {
    if (this.testEmpty(this.data.material_name) || this.testEmpty(this.data.material_num) || this.testEmpty(this.data.unit)) {
      wx.showToast({
        title: '请输入值',
        duration: 1000,
        icon: 'none'
      })
      return;
    }

    const db = wx.cloud.database()
    let that = this

    db.collection('material').where({
      material_name: that.data.material_name
    }).get({
      success: function(res) {
        let len = res.data.length
        if (len > 0) {
          that.setData({
            doc_id: res.data[0]._id,
            fileId: that.data.fileId==''?res.data[0].fileId:that.data.fileId
          })
        }
        console.log('radio-----'+that.data.radio)
        switch (that.data.radio) {
          case 'add':
            if (len > 0) {
              wx.showToast({
                title: '物料已经存在',
                duration: 1000,
                icon: 'none'
              })
              return;
            }
            that.AddFunction(db)
            break;
          case 'update':
            if (len <= 0) {
              wx.showToast({
                title: '物料不存在',
                duration: 1000,
                icon: 'none'
              })
              return;
            }
            that.UpdateFunction(db)
            break;
          case 'delete':
            if (len <= 0) {
              wx.showToast({
                title: '物料不存在',
                duration: 1000,
                icon: 'none'
              })
              return;
            }
            that.DeleteFunction(db)
            break;
          default:
            break;


        }

      }
    })

  },

  //新增操作
  AddFunction(db) {
    wx.showLoading({
      title: '保存中',
      mask: 'true'
    })
    db.collection('material').add({
      data: {
        material_name: this.data.material_name,
        material_num: this.data.material_num,
        unit: this.data.unit,
        fileId:this.data.fileId,
        time: new Date()
      },
      success: function(res) {
        wx.hideLoading()
        wx.showToast({
          title: '添加成功',
          icon: 'success',
          duration: 1000
        })
        setTimeout(function() {
          wx.navigateBack()
        }, 1500)
      }
    })

  },

  //修改操作
  UpdateFunction(db) {
    console.log(this.data.doc_id)
    wx.showLoading({
      title: '保存中',
      mask: 'true'
    })

  /** 本地方法有权限限制，使用云函数代替  db.collection('material').doc(this.data.doc_id).update({
      data: {
        material_num: this.data.material_num,
        unit: this.data.unit,
        time: new Date()
      },
      success: function(res) {
        wx.showToast({
          title: '修改成功',
          icon: 'success',
          duration: 1000
        })
        setTimeout(function() {
          wx.navigateBack()
        }, 1500)
      }
    })  **/

    //云函数代替本地方法
      wx.cloud.callFunction({
        name:'updateSetting',
        data:{
          _id: this.data.doc_id,
          material_num: this.data.material_num,
          unit: this.data.unit,
          fileId:this.data.fileId
        },
        success:function(res){
          wx.hideLoading()
          wx.showToast({
            title: '修改成功',
            icon: 'success',
            duration: 1000
          })
          setTimeout(function () {
            wx.navigateBack()
          }, 1500)
        }
      })


  },

  //删除操作
  DeleteFunction(db) {
    console.log(this.data.doc_id)
    wx.showLoading({
      title: '删除中',
      mask: 'true'
    })
  /** 用云函数代替本地  db.collection('material').doc(this.data.doc_id).remove({
      success: function(res) {
        wx.showToast({
          title: '删除成功',
          icon: 'success',
          duration: 1000
        })
        setTimeout(function() {
          wx.navigateBack()
        }, 1500)
      }
    })  **/

    //云函数
    wx.cloud.callFunction({
      name:'deleteSetting',
      data:{
        _id: this.data.doc_id
      },
      success:function(res){
        wx.hideLoading()
        wx.showToast({
          title: '删除成功',
          icon: 'success',
          duration: 1000
        })
        setTimeout(function () {
          wx.navigateBack()
        }, 1500)
      }
    })

  },

  testEmpty(val) {
    if (val == '' || val == 'undefined' || typeof val == 'undefined') {
      return true;
    } else {
      return false;
    }
  },

  //图片
  // 上传图片
  chooseImg: function (e) {
    var that = this;
    var imgs = this.data.imgs;
    if (imgs.length >= 1) { //一张图
      this.setData({
        lenMore: 1
      });
      setTimeout(function () {
        that.setData({
          lenMore: 0
        });
      }, 2500);
      return false;
    }
    wx.chooseImage({
      count: 1, 
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        var tempFiles = res.tempFiles;//文件列表
        var imgs = that.data.imgs;
        // console.log(tempFilePaths + '----');
        for (var i = 0; i < tempFilePaths.length; i++) {
          if (imgs.length >= 9) {
            that.setData({
              imgs: imgs
            });
            return false;
          } else {
            that.setData({
              cloudPath: tempFiles[i].size + 'img' + Math.ceil(Math.random() * 1000)  //设置一个cloudpath
            })
            console.log(that.data.cloudPath)
            imgs.push(tempFilePaths[i]);
          }
        }
        // console.log(imgs);
        that.setData({
          imgs: imgs
        });
      }
    });
  },

  //上传图片
  uploadImg(){
    let that = this
    wx.showLoading({
      title: '正在上传。。',
      mask:'true'
    })
    wx.cloud.uploadFile({
      cloudPath: this.data.cloudPath,
      filePath: this.data.imgs[0], // 文件路径
      success: res => {
        wx.hideLoading()
        wx.showToast({
          title: '上传成功',
          icon: 'success',
          duration: 1000
        })
       // console.log(res.fileID)
       that.setData({
         fileId:res.fileID
       })
      },
      fail: err => {
        // handle error
      }
    })

  },
  // 删除图片
  deleteImg: function (e) {
    var imgs = this.data.imgs;
    var index = e.currentTarget.dataset.index;
    imgs.splice(index, 1);
    this.setData({
      imgs: imgs
    });
  },
  // 预览图片
  previewImg: function (e) {
    //获取当前图片的下标
    var index = e.currentTarget.dataset.index;
    //所有图片
    var imgs = this.data.imgs;
    wx.previewImage({
      //当前显示图片
      current: imgs[index],
      //所有图片
      urls: imgs
    })
  },
})