// app.ts
App({
  globalData: {
    safeTop: 0,
  },
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    const rect = wx.getMenuButtonBoundingClientRect();
    const deviceInfo = wx.getWindowInfo();
    console.log(deviceInfo);
    this.globalData.safeTop = rect.height + deviceInfo.safeArea.top;
    

    // 登录
    wx.login({
      success: res => {
        console.log(res.code)
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      },
    })
  },
})