<template>
    <div class="single-picture-upload">
        <el-upload class="avatar-uploader" action="https://jsonplaceholder.typicode.com/posts/" :show-file-list="false"
            :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
            <div class="image-url-box" v-if="imageUrl">
                <img :src="imageUrl" class="avatar">
                <div class="image-url-box-delete" @click="handleDeletePicture">
                    <i class="el-icon-delete"></i>
                </div>
            </div>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                imageUrl: '',
            }
        },
        methods: {
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handleDeletePicture(){
                this.imageUrl = "";
            }
        }
    }
</script>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 104px;
        height: 104px;
        line-height: 104px;
        text-align: center;
    }
    .image-url-box {
        position: relative;
        width: 104px;
        height: 104px;
    }
    .image-url-box-delete {
        width:104px;
        height:24px;
        line-height:24px;
        text-align:center;
        background-color: #005cdc;
        opacity: 0.8;
        color:#FFF;
    } 
    .avatar {
        width: 104px;
        height: 104px;
        display: block;
    }
</style>