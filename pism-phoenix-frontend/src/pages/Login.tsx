import { useDeviceMode } from "@/hooks/use-device-mode";
import LoginDesktop from "./login/LoginDesktop";
import LoginMobile from "./login/LoginMobile";

export default function Login() {
    const mode = useDeviceMode();

    if (mode === "mobile") {
        return <LoginMobile />;
    }

    return <LoginDesktop />;
}
