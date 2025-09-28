// src/components/Skills.tsx

import React from "react";
import SectionLayout from "./SectionLayout"; // 共通ラッパーをインポート

// SectionLayout が要求する Props の型を定義
interface SkillsProps {
  imgSrc: string; // Sections から渡される画像のパス
  reverse: boolean; // Sections から渡されるレイアウト反転フラグ
}

const Skills: React.FC<SkillsProps> = ({ imgSrc, reverse }) => {
  return (
    // 💡 共通ラッパーコンポーネントでラップし、コンテンツを子要素として渡す
    <SectionLayout imgSrc={imgSrc} reverse={reverse}>
      {/* 💡 ここに、Skillsセクション独自の文章コンテンツのみを記述します */}
      <h2 className="text-3xl font-bold">. Skills & Tools</h2>
      {/* リストの各要素にも適宜マージンなどのTailwindクラスを追加するのがおすすめです */}
      <ul className="mt-4 list-disc list-inside space-y-2">
        <li>Java（Spring Boot）</li>
        <li>TypeScript（React, Node.js, Express）</li>
        <li>HTML5 / Tailwind CSS</li>
        <li>MySQL / NoSQL（MongoDB）</li>
        <li>Vite / Git / Railway / Vercel</li>
        <li>基本情報 取得 , 応用情報 勉強中</li>
      </ul>
    </SectionLayout>
  );
};
export default Skills;
