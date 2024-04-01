export function isLogin() {
  if (localStorage.getItem("corst_token") == null) {
    return false;
  } else {
    //TODO Check expired token
    return true;
  }
}
