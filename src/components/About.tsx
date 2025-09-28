// src/components/About.tsx

import React from "react";
import SectionLayout from "./SectionLayout"; // 共通ラッパーをインポート

// 💡 SectionLayout が要求する Props の型を定義
interface AboutProps {
  imgSrc: string; // Sections から渡される画像のパス
  reverse: boolean; // Sections から渡されるレイアウト反転フラグ
}

const About: React.FC<AboutProps> = ({ imgSrc, reverse }) => {
  return (
    // 💡 共通ラッパーコンポーネントでラップし、コンテンツ（文章）を子要素として渡す
    <SectionLayout
      imgSrc={imgSrc} // SectionLayout に画像パスを渡す
      reverse={reverse} // SectionLayout に反転フラグを渡す
    >
      {/* 💡 ここに、Aboutセクション独自の文章コンテンツのみを記述します */}
      <h2 className="text-3xl font-bold">. About Me</h2>
      <p className="mt-4">元プロゲーマーエンジニア。現在はWebアプリを個人開発中。</p>
      <p className="mt-2">主にJavaとTypeScript使ってアウトプットを続けています。</p>
      <p className="mt-2">読書が好きで、図書館にまつわる気づきをnoteで発信しています。</p>
      <p className="mt-4">
        ▶︎{" "}
        <a
          href="https://note.com/bonchan_create/n/n98023562840a"
          target="_blank"
          rel="noopener noreferrer"
          className="font-bold text-yellow-500 hover:underline"
        >
          図書館は「雑に使う」と100倍楽しい（noteリンク）
        </a>
      </p>
    </SectionLayout>
  );
};
export default About;
