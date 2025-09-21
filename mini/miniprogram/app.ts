import {BASE_URL, openId} from './utils/util'
App({
  globalData: {
    safeTop: 0,
    navBarHeight: 0,
    menuRight: 0,
    menuTop: 0,
    menuHeight: 0,
    menuWidth:0,
    openId: "",
  },
  BASE_URL: BASE_URL,
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    const rect = wx.getMenuButtonBoundingClientRect();
    const winInfo = wx.getWindowInfo();
    this.globalData.safeTop = rect.height + winInfo.safeArea.top;
    // 胶囊按钮位置信息
    const menuButtonInfo = wx.getMenuButtonBoundingClientRect();
    console.log(menuButtonInfo)
    // 导航栏高度 = 状态栏高度 + 44
    this.globalData.navBarHeight = winInfo.statusBarHeight + 44;
    this.globalData.menuRight = winInfo.screenWidth - menuButtonInfo.right;
    this.globalData.menuTop = menuButtonInfo.top;
    this.globalData.menuHeight = menuButtonInfo.height;
    this.globalData.menuWidth = menuButtonInfo.width;
    // 登录
    wx.login({
      success: res => {
        console.log(res.code);
        wx.request({
          url: this.BASE_URL + '/uid',
          method: 'POST',
          data: {"code": res.code},
          header: {'content-type': 'application/json'},
          success: (res: any) => {
            this.globalData.openId = res.data.openId;
            console.log(res.data.openId);
          },
          fail:(res: any)=> {
            console.log(res);
          }
        });

      },
    })
  },
})

export const app = getApp();