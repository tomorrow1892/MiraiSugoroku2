# MiraiSugoroku2

## ローカルでMiraiSugorokuをテストする手順

### 0. 事前準備
- MiraiSugoroku2プロジェクトの\src\main\resourcesにある"sugoroku.sql"ファイルを，c:\mysql\bin(いつもmysqlやmysqldを立ち上げている場所)にコピーする．
- mysqldを立ち上げる
```
cd c:\mysql\bin
c:\mysql\bin> mysqld --console
(別のコマンドプロンプトを立ち上げる)
```

### 1.データベース"sugoroku"を作成(作ってない場合)
```
cd c:\mysql\bin
c:\mysql\bin> mysql -u root -p
Enter Password: (MySQLのrootパスワード)
mysql> create database sugoroku;
OK...
mysql> create user sugoroku identified by 'sugoroku';
OK...
mysql> grant all on sugoroku.* to sugoroku;    
OK...
mysql> 
```
### 1-a. 既にデータベースを作っている場合，sugorokuDBのTableを全て削除
```
(mysqlにログイン後)
mysql> use sugoroku;
mysql> DROP TABLE `board`, `event`, `player`, `square`, `square_creator`, `square_event`, `sugoroku`;
(エラーが出た場合は初めからtableがないのでOK)
```
- phpMyAdminから削除したほうが簡単?

### 2. 事前準備でコピーしたsugoroku.sqlファイルから，データをインポート
```
mysql> use sugoroku;
mysql> source sugoroku.sql;
```

### 3. F5でプロジェクト実行
- ログインページのURL
  - http://localhost:2289/
- マス作成者ログイン
  - イベント: 公民未来イベント1
  - ユーザID: taro


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
(main) git switch develop
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
(hoge) git switch develop
(develop) git pull origin develop
(develop) git checkout hoge
(hoge) git merge origin develop
```
- 最後のとこでコンフリクトが起きたら、コンフリクトを解決してadd〜pushをやり直す

4. pull requestを送る

