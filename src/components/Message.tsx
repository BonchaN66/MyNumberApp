// src/components/Message.tsx

import React from "react";
import SectionLayout from "./SectionLayout"; // 共通ラッパーをインポート

// SectionLayout が要求する Props の型を定義
interface MessageProps {
  imgSrc: string; // Sections から渡される画像のパス
  reverse: boolean; // Sections から渡されるレイアウト反転フラグ
}

const Message: React.FC<MessageProps> = ({ imgSrc, reverse }) => {
  return (
    // 💡 共通ラッパーコンポーネントでラップし、コンテンツを子要素として渡す
    <SectionLayout imgSrc={imgSrc} reverse={reverse}>
      {/* 💡 ここに、Messageセクション独自の文章コンテンツのみを記述します */}
      <h2 className="text-3xl font-bold">. What Drives Me</h2>
      <p className="mt-4">「プログラマーってかっこいい！」という憧れから学び始めました。</p>
      <p className="mt-2">今は“プログラミング＝ものづくりと課題解決”だと実感しています。</p>
      <p className="mt-2">ただの勉強は今も得意ではありません。</p>
      <p className="mt-2">
        でも「作りたいものがある」「不便を解決したい」と思った瞬間、必要な技術を調べて、何時間でも集中できる。
      </p>
      <p className="mt-2">そういう自分に気づいてから、プログラミングが本当に好きになりました。</p>
    </SectionLayout>
  );
};
export default Message;
