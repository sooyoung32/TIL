---
layout: post
title:  "gitignore 적용 시키기"
date:   2016-08-01
author: Sooyoung
categories: dev
tags: jekyll git
cover:  "/assets/img/git.png"
---


## gitignore 적용 시키기
gitignore 가 적용되지 않고 서버에 올라갔을때 gitignore를 적용시켜 다시 올리기!!

```
git rm -r --cached .
git add .
git commit -m "gitignore applied!!"
git push -u origin master
```