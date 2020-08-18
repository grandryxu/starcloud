// 云函数入口文件
const cloud = require('wx-server-sdk')

cloud.init()
const db = cloud.database()
const _ = db.command

// 云函数入口函数
exports.main = async (event, context) => {
  const wxContext = cloud.getWXContext()

   try{

     return await db.collection('material').doc(event._id).update({
         data: {
           material_num: event.material_num,
           unit: event.unit,
           fileId:event.fileId,
           time: new Date()
         }
       

     })

   }catch(e){
     console.log(e)
   }
}