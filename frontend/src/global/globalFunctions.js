import { serverAdress } from "./globalVaribles.js";

export async function isLogin() {
  if (localStorage.getItem("corst_token") == null) {
    return false;
  } else {
    try {
      const response = await fetch(
        serverAdress +
          "/auth/check_login?jwt=" +
          localStorage.getItem("corst_token"),
        { method: "GET" }
      );
      if (!response.ok) {
        localStorage.removeItem("corst_token");
        return false;
      }
    } catch (error) {
      console.error("Error:", error);
      return false;
    }
    return true;
  }
}

export async function getAuthorities() {
  try {
    const response = await fetch(serverAdress + "/auth/get_auth_info", {
      method: "GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("corst_token"),
      },
    });
    if (!response.ok) {
      return null;
    }
    const data = await response.json();
    return data.authorities;
  } catch (error) {
    console.error("Error:", error);
    return null;
  }
}

export async function checkAuthorities(authority) {
  const authorities = await getAuthorities();
  if (authorities == null) {
    return false;
  }
  return authorities.some((auth) => auth.authority === authority);
}
