# MiraiSugoroku2

## git pushまでにやること
```
(作業ブランチで)
git fetch
git rebase "親ブランチ"
```


## git 運用のルール
- 1 issue 1 branch
- main → develop
  - develop → game_base
  - game_base においてgame関連のissueに関するbranchを立てる
