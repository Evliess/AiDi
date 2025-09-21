interface RequestOptions {
  url: string,
  method: string,
  data?: any,
  header?: any
}
export const openId = "";
export const token = "";
export const BASE_URL = 'http://localhost:8080/public/v1';
export const request = <T = any>(options: RequestOptions) : Promise<T> => {
  return new Promise((resolve, reject)=> {
    wx.request({
      url: BASE_URL + options.url,
      method: options.method,
      data: options.data || {},
      header: options.header || {},
      timeout: 120000,
      success: (res) => {
        if(res.statusCode == 200) {
          resolve(res.data as T);
        } else {
          reject(res)
        }
      },
      fail: (err) => {reject(err);},
    });
  });
}

//新增会员
export const addVip = (url: string, openId: string, token: string, data:any) => request<any>({url: url, method: 'POST', data: data, header: {'content-type': 'application/json', 'X-token': token, 'X-openId': openId}});

//会员充值
export const addCharge = (url: string, openId: string, token: string, data:any) => request<any>({url: url, method: 'POST', data: data, header: {'content-type': 'application/json', 'X-token': token, 'X-openId': openId}});

//根据手机号查找会员
export const findVipByPhone = (url: string, openId: string, token: string,) => request<any>({url: url, method: 'GET', data: {}, header: {'content-type': 'application/json', 'X-token': token, 'X-openId': openId}});

//根据手机号更新剩余次数
export const updateLeftCountByPhone = (url: string, openId: string, token: string,) => request<any>({url: url, method: 'PUT', data: {}, header: {'content-type': 'application/json', 'X-token': token, 'X-openId': openId}});

//根据手机号更新积分
export const updateScoreByPhone = (url: string, openId: string, token: string, data: any) => request<any>({url: url, method: 'POST', data: data, header: {'content-type': 'application/json', 'X-token': token, 'X-openId': openId}});
//获取划卡项目
export const findPlayItems = (url: string, openId: string, token: string,) => request<any>({url: url, method: 'GET', data: {}, header: {'content-type': 'application/json', 'X-token': token, 'X-openId': openId}});

export const addVipPlay = (url: string, openId: string, token: string, data: any,) => request<any>({url: url, method: 'POST', data: data, header: {'content-type': 'application/json', 'X-token': token, 'X-openId': openId}});

export const viewVip = (url: string, openId: string, token: string,) => request<any>({url: url, method: 'GET', data: {}, header: {'content-type': 'application/json', 'X-token': token, 'X-openId': openId}});

export const updateByPhone = (url: string, openId: string, token: string, type: string, leftCount: string, expiredAt: string) => request<any>({url: url, method: 'PUT', data: {"type": type, "leftCount":leftCount, "expiredAt":expiredAt}, header: {'content-type': 'application/json', 'X-token': token, 'X-openId': openId}});



