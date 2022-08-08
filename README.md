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

## 運用案　中橋
### 初めにやること
- developブランチをmainにマージして、developブランチを消す
- 皆クローンをし直す
- (最初mainブランチだけの状態でスタート)
- 誰かがmainブランチからdevelopブランチを切る
  - 切ったらすぐpush
- 皆んながpullする
```
(main) git pull --all
```

### 普段やること
1. 作業ブランチをきって作業する
```
(main) git checkout develop
(develop) git branch hoge
(develop) git checkout hoge
```
2. 作業をこまめに作業ブランチのリモートリポジトリにコミットする
```
(hoge) git add .
(hoge) git commit -m "hoge"
(hoge) git push origin hoge
```
3. pull requestを送りたい場合は、リモートの最新版developブランチをローカルの作業ブランチに反映する
```
(hoge) git add .
(hoge) git commit -m "hoge"
(hoge) git push origin hoge
(hoge) git checkout develop
(develop) git pull origin develop
(develop) git checkout hoge
(hoge) git merge origin develop
```
- 最後のとこでコンフリクトが起きたら、コンフリクトを解決してadd〜pushをやり直す

4. pull requestを送る




