import LandingPage from "./components/LandingPage";
import Section from "./Sections";
import Footer from "./components/Footer";

const App = () => {
  return (
    <>
      <LandingPage /> {/* ランディング→ヒーローヘッダーを包括 */}
      <Section />
      <Footer />
    </>
  );
};

export default App; //export const App = () => {}でリファクタ検討
