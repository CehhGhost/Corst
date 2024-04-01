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
