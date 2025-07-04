# MyNumber簡易管理アプリ（ローカル専用）

## 📌 概要

このアプリは、社員の「氏名・マイナンバー・備考」などの情報を  
**ローカル環境で簡易管理するWebアプリケーション**です。  
Spring Bootを用いたMVC構成で、**新規登録・一覧表示・削除機能**を備えています。

Java/Springの基本的な理解と、Controller・Model・View連携の把握を目的として開発しました。

---

## 🛠️ 使用技術

- Java 17
- Spring Boot 3.x
- Thymeleaf
- HTML5 / CSS（最低限）
- IntelliJ IDEA
- Maven
- ローカル実行のみ（Tomcat内蔵）

---

## ✨ 主な機能

| 機能 | 内容 |
|------|------|
| 新規登録 | 社員ID、氏名、マイナンバー、備考を登録 |
| 一覧表示 | 登録情報を表形式で表示（登録日付き） |
| 削除 | 社員IDをキーに1件ずつ削除可能 |
| 登録日自動付与 | `LocalDate.now()` による自動設定 |

---

## 📷 画面構成

- `/form`：新規登録フォーム  
- `/list`：一覧表示（削除ボタン付き）

![新規登録フォームのスクリーンショット](img/form.png)

![一覧表示画面のスクリーンショット](img/list.png)

> デザインは機能重視の最低限。UXやレスポンシブ対応は今後拡張予定。

---

## ⚠️ 制限事項（意図的に省いた点）

| 省略した要素 | 理由・補足 |
|---------------|------------|
| 永続化（DB/CSV） | 開発初期フェーズにつき、Javaの`ArrayList`を使用。今後はH2またはMySQLへ移行予定。 |
| 編集機能（Update） | 基本CRUDのC・R・Dを優先。更新機能は今後の実装候補。 |
| 入力バリデーション | HTMLの制約（`required`や`pattern`）のみに留め、`@Valid`等は未導入。 |
| 認証・認可（ログイン機能） | ローカル用途に限定しているため、セキュリティ機能は除外。 |
| レスポンシブデザイン | 今回はサーバーサイドロジックに集中。UI拡張は後日対応予定。 |

---

## 🎯 今後の拡張予定

- データのCSV保存機能（I/O処理の習得）
- H2またはMySQLとの接続
- Spring Securityを用いたログイン機能の実装
- 入力バリデーションの追加（`@Valid`, BindingResult 等）
- 編集（Update）機能の実装
- BootstrapなどでのUI改善

---

## 🧠 このアプリで証明できること

- Spring Bootを用いたMVC構成の理解
- コントローラー・モデル・ビューの基本実装能力
- HTMLフォームとJavaクラスの連携方法（データバインド）
- サーバーサイドでの自動処理（登録日付加）
- CRUDアプリの開発フローとバグ修正力

---

## 🚀 実行方法（ローカル）

1. このリポジトリをクローン：
   ```bash
   git clone https://github.com/BonchaN66/MyNumberApp.git
   cd MyNumberApp

2. Mavenビルド：   
   MyNumberAPPをIDEで開いて　MyNumberApplication.java を実行
   （省略　src/main/java/com/example/MyNumber/MyNumberApplication.java）

3.ブラウザでアクセス：

新規登録 → http://localhost:8080/form

一覧表示 → http://localhost:8080/list

👤 制作・開発者
名前：BonchaN

学習目的での個人開発。今後の実務に活かすため、技術習得＋段階的な機能拡張を意識しています。
