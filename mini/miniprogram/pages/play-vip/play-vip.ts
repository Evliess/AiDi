import { updateLeftCountByPhone, updateScoreByPhone,
  findVipByPhone, findPlayItems,addVipPlay } from '../../utils/util'
Page({
  data: {
    items:[],
    selectedValue: "",
    openId: "",
    token: "",
    user: {
      name: "",
      phone: "",
      money: "",
      type: "",
      score: "",
      oldScore: "",
      chineseType: "",
      oldType: "",
      leftCount: "",
      oldLeftCount: "",
      expiredAt: "",
      oldExpiredAt: "",
      oldChargeAt: "",
    }
  },
  onPhoneChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.phone": value, });
  },
  clearData: function () {
    const userInfo: any = this.data.user;
    const newUserInfo: any = {};
    for (let key in userInfo) {
      if (userInfo.hasOwnProperty(key)) {
        newUserInfo[key] = "";
      }
    }
    this.setData({userInfo: newUserInfo, "user.score": "", "selectedValue": "", "user.leftCount": ""});
  },
  onScoreChange: function(e: any){
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.score": value, });
  },

  onLeftCountChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.leftCount": value, });
  },

  async score() {
    try {
      const url = "/consumers-score";
      const data: any = {};
      data.phone = this.data.user.phone;
      data.score = this.data.user.score;
      if(!data.score) {
        wx.showToast({ title: "请设置积分！", duration: 3000, icon: 'error' });
        return;
      }
      await updateScoreByPhone(url, this.data.openId, this.data.token, data);
      this.setData({"user.oldScore": this.data.user.score});
      wx.navigateTo({"url": "/pages/alert-page/alert-page?res=success&msg=积分修改成功!&from=play-vip"});
    } catch(e) {
      wx.navigateTo({"url": "/pages/alert-page/alert-page?res=fail&msg=积分修改失败!&from=play-vip"});
    }
  },
  async play() {
    try{
      if(this.data.user.oldType!="day") {
        this.setData({"user.leftCount": "0"});
      }
      if(!this.data.user.leftCount) {
        wx.showToast({ title: "请设置剩余次数！", duration: 3000, icon: 'error' });
        return;
      }
      const url = "/consumer/left-count/"+this.data.user.phone+"/"+this.data.user.leftCount;
      this.setData({"user.oldLeftCount":this.data.user.leftCount});
      await updateLeftCountByPhone(url, this.data.openId, this.data.token);
      const playUrl = "/consumers-play";
      const data:any = {};
      data.phone = this.data.user.phone;
      data.item = this.data.selectedValue;
      if(data.item.length==0) {
        wx.showToast({ title: "请设置项目！", duration: 3000, icon: 'error' });
        return;
      }
      await addVipPlay(playUrl, this.data.openId, this.data.token, data);
      wx.navigateTo({"url": "/pages/alert-page/alert-page?res=success&msg=划卡成功!&from=play-vip"});
    } catch(e) {
      wx.navigateTo({"url": "/pages/alert-page/alert-page?res=fail&msg=划卡失败!&from=play-vip"});
      return;
    }
  },

  async find() {
    this.clearData();
    try {
      const url = "/consumer/" + this.data.user.phone;
      const findVipByPhoneRes = await findVipByPhone(url, this.data.openId, this.data.token);
      if (findVipByPhoneRes.status == "ng") {
        this.setData({ "user.name": null });
        wx.showToast({ title: findVipByPhoneRes.message,    duration: 3000, icon: 'error' });
        return;
      }
      this.setData({
        "user.name": findVipByPhoneRes.name,
        "user.phone": findVipByPhoneRes.phone,
        "user.oldChargeAt": findVipByPhoneRes.chargeAt,
        "user.oldType": findVipByPhoneRes.type,
      });
      if (findVipByPhoneRes.score) {
        this.setData({ "user.oldScore": findVipByPhoneRes.score });
      } else {
        this.setData({ "user.score": null });
      }
      if (findVipByPhoneRes.type == "day") this.setData({ "user.chineseType": "次卡" });
      if (findVipByPhoneRes.type == "month") this.setData({ "user.chineseType": "月卡" });
      if (findVipByPhoneRes.type == "season") this.setData({ "user.chineseType": "季卡" });
      if (findVipByPhoneRes.type == "year") this.setData({ "user.chineseType": "年卡" });
      if (findVipByPhoneRes.type != "day") {
        this.setData({ "user.oldExpiredAt": findVipByPhoneRes.expiredAt });
      } else {
        this.setData({ "user.oldLeftCount": findVipByPhoneRes.leftCount });
      }
    } catch (e) {
      wx.showToast({ title: '请检查会员号!',    duration: 3000, icon: 'error' });
      return;
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */

  onRadioChange: function(e: any) {
    const selectedValue = e.detail.value;
    this.setData({
      selectedValue: selectedValue
    });
  },
  async onLoad() {
    
  },

  onReady() {},
  async onShow() {
    const openId = wx.getStorageSync("openId");
    const token = wx.getStorageSync("token");
    this.setData({openId: openId, token: token});
    try {
      const url = "/playitems";
      const findPlayItemsRes = await findPlayItems(url, this.data.openId, this.data.token);
      const findItems:any = [];
      findPlayItemsRes.forEach((ele: { name: any; }) => {
        findItems.push({"name":ele.name, "value": ele.name, "checked": false})
      });
      this.setData({items: findItems});
    } catch(e) {
      wx.showToast({ title: '获取项目失败!',    duration: 3000, icon: 'error' });
    }
  },
  onHide() {},
  onUnload() {},
  onPullDownRefresh() {},
  onReachBottom() {},
  onShareAppMessage() {},

})