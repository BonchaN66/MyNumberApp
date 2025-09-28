import Section from "./Sections";
import Footer from "./components/Footer";
import LandingWithHero from "./components/LandingWithHero";

export const App = () => {
  return (
    <>
      <LandingWithHero /> {/* LandingPage + HeroHeader */}
      <Section />
      <Footer />
    </>
  );
};
