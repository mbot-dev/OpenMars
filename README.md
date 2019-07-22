## OpenDolphin Simple
Kazushi Minagawa


#### 1．開発環境
 * openjdk 11.0.2 2019-01-15
 * IntelliJ IDEA Community Edition
 * WildFly-16.0.0.Final
 * Git

#### 2.
 * Launch IntelliJ
 * Select Git from the menu (Check out from Version Control) in the start up panel
 * Enter URL: git@github.com:mbot-dev/OpenMars.git
 * Directory: directory



#### ５．コンパイル  
 * git clone https&#58;//github.com/dolphin-dev/OpenDolphin.git ~/Desktop/OpenDolphin  
 * mvn clean  
 * mvn package  


#### ６．ローカライゼイション  
  * 最後が resources となっているパッケージ（フォルダ）内にクラス別のリソースファイルがあります。
  * 例）open.dolphin.client.ChartImpl クラスのリソース -> open.dolphin.client.resources.ChartImpl.properties
  * これをコピーし、iso3166 国名コードをアンダーバーでつないだファイルとして保存します。
  * 例）タガログ語にする場合は ChartImpl_tl.properties として保存。
  * ChartImpl_国名コード.propertiesファイルの内容をローカライズします。
  * これを全てのリソースファイルについて行います。




#### ９．参考情報
 * [５分間評価](https://gist.github.com/dolphin-dev/d21c88cbfefa86c98049)
 * [設計概要](http://www.digital-globe.co.jp/architecture.html)
 * [Docker イメージ](https://github.com/dolphin-dev/docker-images)
 * [ORCAとの接続](https://gist.github.com/dolphin-dev/c75e4ca63689779bfdf7)
