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

## 試したい運用案　中橋
### 初めにやること
-(最初mainブランチだけの状態でスタート)
-mainブランチからdevelopブランチを切る
  - 切ったらすぐpush
- 皆んながpullする
'''
(main)git pull --all
'''

###普段やること
1. 作業ブランチをきる
1. あ
