import { useDeviceMode } from "@/hooks/use-device-mode";
import HomeDesktop from "./home/HomeDesktop";
import HomeMobile from "./home/HomeMobile";

export default function Home() {
    const mode = useDeviceMode();

    if (mode === "mobile") {
        return <HomeMobile />;
    }

    return <HomeDesktop />;
}
