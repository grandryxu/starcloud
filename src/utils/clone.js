let clone = function(oriObj){
    let toObj;
    if(Array.isArray(oriObj)){
        toObj =[],
        oriObj.forEach(ele => {
            toObj.push(clone(ele));
        });
    }else if(Object.prototype.toString.call(oriObj) === "[object Object]"){
        toObj = {};
        Object.keys(oriObj).forEach(ele=>{
            toObj[ele] = clone(oriObj[ele])
        })
    }else{
        return oriObj
    }

    return toObj;
}
export default clone