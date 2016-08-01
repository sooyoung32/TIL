## gitignore 적용 시키기
gitignore 가 적용되지 않고 서버에 올라갔을때 gitignore를 적용시켜 다시 올리기!!

```
git rm -r --cached .
git add .
git commit -m "gitignore applied!!"
git push -u origin master
```