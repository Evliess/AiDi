const app = getApp()
Component({
  properties: {
  },
  data: {
    navBarHeight: app.globalData.navBarHeight,
    menuRight: app.globalData.menuRight,
    menuTop: app.globalData.menuTop,
    menuHeight: app.globalData.menuHeight,
    menuWidth: app.globalData.menuWidth,
  },
  attached: function () { },
  methods: {
    goHome: function () {
      wx.redirectTo({ url: "/pages/home/home" });
    },
    goBack: function () {
      const pages = getCurrentPages();
      if (pages.length > 1) {
        wx.navigateBack({
          delta: 1
        });
      } else {
        console.log('已经在首页');
      }
    },
  },

})