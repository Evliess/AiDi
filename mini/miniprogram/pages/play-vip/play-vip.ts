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
    this.setData({userInfo: newUserInfo});
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
      await updateScoreByPhone(url, this.data.openId, this.data.token, data);
      wx.showToast({ title: "成功!", duration: 1000, icon: 'success' });
    } catch(e) {
      wx.showToast({ title: "出错!", duration: 1000, icon: 'error' });
    }
  },
  async play() {
    try{
      const url = "/consumer/left-count/"+this.data.user.phone+"/"+this.data.user.leftCount;
      this.setData({"user.oldLeftCount":this.data.user.leftCount});
      await updateLeftCountByPhone(url, this.data.openId, this.data.token);
      wx.showToast({ title: "划卡成功!", duration: 1000, icon: 'success' });
    } catch(e) {
      wx.showToast({ title: "划卡失败！", duration: 1000, icon: 'error' });
      return;
    }
    try {
      const url = "/consumers-play";
      const data:any = {};
      data.phone = this.data.user.phone;
      data.item = this.data.selectedValue;
      await addVipPlay(url, this.data.openId, this.data.token, data);
    } catch(e) {
      wx.showToast({ title: "划卡失败！", duration: 1000, icon: 'error' });
    }
  },

  async find() {
    this.clearData();
    try {
      const url = "/consumer/" + this.data.user.phone;
      const findVipByPhoneRes = await findVipByPhone(url, this.data.openId, this.data.token);
      if (findVipByPhoneRes.status == "ng") {
        this.setData({ "user.name": null });
        wx.showToast({ title: findVipByPhoneRes.message, duration: 1000, icon: 'error' });
        return;
      }
      this.setData({
        "user.name": findVipByPhoneRes.name,
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
      wx.showToast({ title: '请检查手机号!', duration: 1000, icon: 'error' });
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
    const openId = wx.getStorageSync("openId");
    const token = wx.getStorageSync("token");
    this.setData({openId: openId, token: token});
    try {
      const url = "/playitems";
      const findPlayItemsRes = await findPlayItems(url, this.data.openId, this.data.token);
      const findItems:any = [];
      findPlayItemsRes.forEach((ele: { name: any; }) => {
        findItems.push({"name":ele.name, "value": ele.name})
      });
      this.setData({items: findItems});
    } catch(e) {
      wx.showToast({ title: '获取项目失败!', duration: 1000, icon: 'error' });
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})