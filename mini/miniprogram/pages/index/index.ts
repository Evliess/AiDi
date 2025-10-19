import { login } from '../../utils/util'
Page({
  data: {
    safeTop: 0,
    user: {
      name: "",
      pass: "",
    },
  },
  async confirm() {
    try{
      const url = "/login";
      const data:any = {};
      data.name = this.data.user.name;
      data.accessKey = this.data.user.pass;
      const loginRes = await login(url, data);
      if(loginRes.status=="ok") {
        wx.setStorageSync("token", loginRes.token+"."+data.name);
        wx.redirectTo({"url": "/pages/home/home"});
      } else {
        wx.setStorageSync("token", null);
      }
    } catch(e) {
      wx.showToast({ title: '登录失败!',    duration: 3000, icon: 'error' });
    }
  },
  clearData: function() {
    this.setData({"user.name": "", "user.pass": ""});
  },

  onNameChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.name": value, });
  },
  onPassChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.pass": value, });
  },
  onLoad: function() {
  },
})
