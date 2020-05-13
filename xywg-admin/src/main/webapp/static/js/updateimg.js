	window.onload = function() {
				var input = document.getElementById("upgteimg");
				var showui = document.getElementById("showui");
				var result;
				var dataArr = []; // 储存所选图片的结果(文件名和base64数据)
				var fd; //FormData方式发送请求
				var showinput = document.getElementById("showinput");
				var oSubmit = document.getElementById("submit");
				var dateli, dateinput;
				function randomString(len) {　　
					len = len || 32;　　
					var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/ 　　
					var maxPos = $chars.length;　　
					var pwd = '';　　
					for(i = 0; i < len; i++) {　　　　
						pwd += $chars.charAt(Math.floor(Math.random() * maxPos));　　
					}　　
					return pwd;
				}
				console.log()
				if(typeof FileReader === 'undefined') {
					alert("抱歉，你的浏览器不支持 FileReader");
					input.setAttribute('disabled', 'disabled');
				} else {
					input.addEventListener('change', readFile, false);
				}

				function readFile() {
					fd = new FormData();
					var iLen = this.files.length;
					var index = 0;
					var currentReViewImgIndex = 0;
					for(var i = 0; i < iLen; i++) {
						if(!input['value'].match(/.jpg|.gif|.png|.jpeg|.bmp/i)) {　　 //判断上传文件格式
							return alert("上传的图片格式不正确，请重新选择");
						}
						var reader = new FileReader();
						reader.index = i;
						fd.append(i, this.files[i]);
						reader.readAsDataURL(this.files[i]); //转成base64
						reader.fileName = this.files[i].name;
						reader.files = this.files[i];
						reader.onload = function(e) {
							var imgMsg = {
								name: randomString(5), //获取文件名
								base64: this.result, //reader.readAsDataURL方法执行完后，base64数据储存在reader.result里
							}
							dataArr.push(imgMsg);
							for(var j = 0; j < dataArr.length; j++) {
								currentReViewImgIndex = j
							}
							result = '<div class="showdiv"><img class="left" src="../../static/img/Arrow_left.svg" /><img class="center" src="../../static/img/delete.svg" /><img class="right" src="../../static/img/Arrow_right.svg" /></div><img id="img' +currentReViewImgIndex+randomString(1)+randomString(2) +randomString(5) + '" class="showimg" src="' + this.result + '" />';
							var li = document.createElement('li');
							li.innerHTML = result;
							showui.appendChild(li);
							index++;
						}
					}
				}
				
				//初始化内容
		         var val = $("#businessMaterial").val();
		         if(val){
		         var arr = val.split('||');
		         for (var i in arr) {
		        	 let a = arr[i];
		        	 $("#showui").append('<li><div class="showdiv"><img class="left" src="../../static/img/Arrow_left.svg" /><img class="center" src="../../static/img/delete.svg" /><img class="right" src="../../static/img/Arrow_right.svg" /></div><img id="img' +randomString(1)+randomString(2) +randomString(5) + '" class="showimg" src="/labor/' + a + '" /></li>');
		        	 }
		         }

				function onclickimg() {
					var dataArrlist = dataArr.length;
					var lilength = document.querySelectorAll('ul#showui li');
					for(var i = 0; i < lilength.length; i++) {
						lilength[i].getElementsByTagName('img')[0].onclick = function(num) {
							return function() {
								if(num == 0) {} else {
									var list = 0;
									for(var j = 0; j < dataArr.length; j++) {
										list = j
									}
									var up = num - 1;
									dataArr.splice(up, 0, dataArr[num]);
									dataArr.splice(num + 1, 1)
									var lists = $("ul#showui li").length;
									for(var j = 0; j < lists; j++) {
										var usid = $("ul#showui li")[j].getElementsByTagName('img')[3];
										$("#" +usid.id+ "").attr("src", dataArr[j].base64)
									}
								}
							}
						}(i)
						//删除图标
						lilength[i].getElementsByTagName('img')[1].onclick = function(num) {
							return function() {
								if(dataArr.length == 1) {
									dataArr = [];
									$("ul#showui").html("")
								} else {
									$("ul#showui li:eq(" + num + ")").remove()
									dataArr.splice(num, 1)
								}

							}
						}(i)
						//右箭头图标
						lilength[i].getElementsByTagName('img')[2].onclick = function(num) {
							return function() {
								var list = 0;
								for(var j = 0; j < dataArr.length; j++) {
									list = j
								}
								var datalist = list + 1;
								dataArr.splice(datalist, 0, dataArr[num]);
								dataArr.splice(num, 1)
								var lists = $("ul#showui li").length;
								for(var j = 0; j < lists; j++) {
									var usid = $("ul#showui li")[j].getElementsByTagName('img')[3];
									$("#" + usid.id + "").attr("src", dataArr[j].base64)
								}

							}
						}(i)

					}
				}
				showui.addEventListener("click", function() {
					onclickimg();
				}, true)

				function send() {
					$("#businessMaterial").val("");
					for(var j = 0; j < dataArr.length; j++) {
						let base64Codes = dataArr[j].base64;
						fileUpload(base64Codes);
						sleep(500);
					}
				}

				oSubmit.onclick = function() {
					if(!dataArr.length) {
						return alert('请先选择文件');
					}
					send();
				}
				
				//上传图片
				function fileUpload(base64Codes) {
					var myform = new FormData();
				
//					alert(_name);
//					$("#faceImg").attr('src', _name);
				    
				    myform.append('file', convertBase64UrlToBlob(base64Codes));
				    $.ajax({
				        url: Feng.ctxPath + "/lxftp/upload",
				        type: "POST",
				        data: myform,
				        contentType: false,
				        processData: false,
				        success: function (data) {
				        	if(200 != data.code){
				        	
				        		Feng.error(data.message);
				        	}else{
				        		
		                      if($("#businessMaterial").val()){
		                    	  $("#businessMaterial").val($("#businessMaterial").val() + "||" + data.path);
		                      }else{
		                    	  $("#businessMaterial").val(data.path);
		                      }
		                      Feng.success("上传成功");
				        	}
				        	},
				        error: function (data) {
				            Feng.error("上传失败!" + data.responseJSON.message + "!");
				            
				        }

				    });
				}
				
				/**  
				 * 将以base64的图片url数据转换为Blob  
				 * @param urlData  
				 *            用url方式表示的base64图片数据  
				 */  
				function convertBase64UrlToBlob(urlData){  

				    var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte  

				    //处理异常,将ascii码小于0的转换为大于0  
				    var ab = new ArrayBuffer(bytes.length);  
				    var ia = new Uint8Array(ab);  
				    for (var i = 0; i < bytes.length; i++) {  
				        ia[i] = bytes.charCodeAt(i);  
				    }  

				    return new Blob( [ab] , {type : 'image/png'});  
				} 
				
				
				/**
				 *	sleep
				 * @param numberMillis
				 * @returns
				 */
				function sleep(numberMillis) {
				    var now = new Date();
				    var exitTime = now.getTime() + numberMillis;
				    while (true) {
				        now = new Date();
				        if (now.getTime() > exitTime)
				            return;
				    }
				}

			}
		