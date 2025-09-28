import { motion } from "framer-motion";
import Message from "./components/Message";
import About from "./components/About";
import Hobbies from "./components/Hobbies";
import Skills from "./components/Skills";
import RisingDots from "./components/RisingDots";

// 💡 セクションコンポーネントが受け取るPropsの型
interface SectionComponentProps {
  imgSrc: string;
  reverse: boolean;
}

// 💡 データ配列の型 (ComponentTypeを使用)
type SectionData = {
  component: React.ComponentType<SectionComponentProps>;
  imgSrc: string;
  reverse: boolean;
  id: string;
};

export default function Sections() {
  const sectionVariants = {
    left: { opacity: 0, x: -50 },
    right: { opacity: 0, x: 50 },
    visible: { opacity: 1, x: 0 },
  };

  // 💡 子コンポーネントに渡すデータのみを管理
  const sections: SectionData[] = [
    // 💡 id プロパティを追加
    { component: Message, imgSrc: "/img/message.jpg", reverse: false, id: "message" },
    { component: About, imgSrc: "/img/fps.jpg", reverse: true, id: "about" },
    { component: Hobbies, imgSrc: "/img/hobbies.jpg", reverse: false, id: "hobbies" },
    { component: Skills, imgSrc: "/img/skills.jpg", reverse: true, id: "skills" },
  ];

  return (
    <main className="relative bg-[url('/img/b-g.jpg')] bg-cover bg-center mb:bg-fixed py-30">
      {/* ↑ py-30でセクション(body全体)上下に余白追加 */}

      {/* 水玉 */}
      <div className="absolute inset-0 -z-10 bg-cover bg-center" />
      <div className="absolute inset-0 z-0 pointer-events-none">
        <RisingDots />
      </div>

      {/* セクション本体 */}
      <div className="w-auto relative flex flex-col gap-12 md:gap-20 px-4 lg:px-24">
        {sections.map((sec, idx) => {
          const Component = sec.component;
          return (
            <motion.div
              key={idx}
              // 💡 役割: セクション全体の出現アニメーションを制御
              id={sec.id} // 💡 役割: セクションの識別子を設定 ナビのリンクに使用中
              className="opacity-0"
              initial={sec.reverse ? "right" : "left"}
              whileInView="visible"
              variants={sectionVariants}
              viewport={{ once: true, amount: 0.2 }}
            >
              {/* 💡 役割: 子コンポーネントをレンダリングし、必要なPropsを渡す */}
              <Component imgSrc={sec.imgSrc} reverse={sec.reverse} />
            </motion.div>
          );
        })}
      </div>
    </main>
  );
}
