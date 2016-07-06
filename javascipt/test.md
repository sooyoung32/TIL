## Test

```java
	@RequestMapping("/login")
	public String login(
			HttpServletRequest request
			, HttpServletResponse response
			, Model model
			) throws Exception {
		
		HttpSession session = request.getSession(false);
		UserBean userBean = SessionUtil.getUserLoginInfo(session);			// Login 세션
		
		if (userBean != null) {
			return "redirect:" + IMenuConstant._URL_MAINPAGE_AFTER_LOGIN;
		}
		
		return "login/login";
	}
```