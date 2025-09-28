// src/components/Hobbies.tsx

import React from "react";
import SectionLayout from "./SectionLayout"; // 共通ラッパーをインポート

// 💡 SectionLayout が要求する Props の型を定義
interface HobbiesProps {
  imgSrc: string; // Sections から渡される画像のパス
  reverse: boolean; // Sections から渡されるレイアウト反転フラグ
}

const Hobbies: React.FC<HobbiesProps> = ({ imgSrc, reverse }) => {
  return (
    // 💡 共通ラッパーコンポーネントでラップし、コンテンツ（文章）を子要素として渡す
    <SectionLayout imgSrc={imgSrc} reverse={reverse}>
      {/* 💡 ここに、Hobbiesセクション独自の文章コンテンツのみを記述します */}
      <h2 className="text-3xl font-bold">. My Hobbies</h2>
      <p className="mt-4">
        近所の公園で読書をするのが好きです。ジャンルは日本文学とビジネス書全般。
      </p>
      <p className="mt-2">特に心に残っている一冊は、東野圭吾さんの『クスノキの番人』です。</p>
      <p className="mt-2">読書を通じて得た知識や感動を、日々の生活や自己研鑽に活かしています。</p>
    </SectionLayout>
  );
};
export default Hobbies;
