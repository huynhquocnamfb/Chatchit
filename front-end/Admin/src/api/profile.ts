import { APIClient, setAuthorization,getAuthResponse } from "./apiCore";
import * as url from "./urls";

const api = new APIClient();

const getProfileDetails = () => {
  const accessToken = getAuthResponse().accessToken;
  console.log(accessToken) // jwt
  setAuthorization(accessToken)
  return api.get(url.GET_MY_PROFILE); // edit payload.data.data in profile reducer

};

const getSettings = () => {
  return api.get(url.GET_USER_SETTINGS_2);
};
const updateSettings = (field: string, value: any) => {
  return api.update(url.UPDATE_ETTINGS, {
    field: field,
    value: value,
  });
};

export { getProfileDetails, getSettings, updateSettings };
