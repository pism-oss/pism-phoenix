import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Lock, User, ShieldCheck } from "lucide-react";
import { nanoid } from "nanoid";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useToast } from "@/hooks/use-toast";
import api from "@/lib/api";
import { getPublicKey, encryptText } from "@/lib/auth";
import type { LoginResponse } from "@/lib/auth";

export default function LoginMobile() {
    const [account, setAccount] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
    const { toast } = useToast();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!account || !password) {
            toast({ variant: "destructive", title: "错误", description: "请输入账号和密码。" });
            return;
        }

        setIsLoading(true);
        try {
            const keyId = nanoid();
            const publicKey = await getPublicKey(keyId);
            const encryptedAccount = encryptText(account, publicKey);
            const encryptedPassword = encryptText(password, publicKey);

            await api.post<LoginResponse>("/open/cas/login", {
                account: encryptedAccount,
                password: encryptedPassword,
                keyId: keyId,
            }).then((response) => {
                const { token, account: userAccount, email } = response.data;
                localStorage.setItem("pism_token", token.token);
                localStorage.setItem("pism_token_name", token.tokenName);
                localStorage.setItem("pism_user", JSON.stringify({ account: userAccount, email }));
                toast({ title: "成功", description: "登录成功" });
                navigate("/");
            });
        } catch (error: any) {
            toast({
                variant: "destructive",
                title: "登录失败",
                description: error.message || error.response?.data?.msg || "账号或密码错误",
            });
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-white flex flex-col p-6 overflow-hidden">
            {/* Immersive Top Identity */}
            <div className="mt-12 mb-16 flex flex-col items-center animate-in slide-in-from-top-12 duration-1000">
                <div className="w-24 h-24 bg-primary/5 rounded-[32px] flex items-center justify-center mb-8 shadow-inner ring-1 ring-primary/10">
                    <ShieldCheck className="w-12 h-12 text-primary" />
                </div>
                <h1 className="text-4xl font-black text-slate-900 tracking-tighter mb-2">Pism Phoenix</h1>
                <p className="text-slate-400 font-bold tracking-widest text-xs uppercase opacity-60">Mobile Management Kit</p>
            </div>

            {/* Login Form - Centered or Bottom Anchored */}
            <div className="flex-1 flex flex-col justify-center max-w-sm mx-auto w-full animate-in fade-in duration-1000 delay-300">
                <form onSubmit={handleLogin} className="space-y-10">
                    <div className="space-y-6">
                        <div className="relative">
                            <div className="absolute left-6 top-1/2 -translate-y-1/2 h-6 w-6 text-slate-300">
                                <User />
                            </div>
                            <Input
                                type="text"
                                placeholder="账号名称"
                                className="h-20 pl-16 pr-8 text-xl rounded-[24px] bg-slate-50 border-none shadow-sm focus-visible:ring-1 focus-visible:ring-primary/20 transition-all font-bold placeholder:text-slate-200 placeholder:font-medium"
                                value={account}
                                onChange={(e) => setAccount(e.target.value)}
                                disabled={isLoading}
                                autoComplete="username"
                            />
                        </div>
                        <div className="relative">
                            <div className="absolute left-6 top-1/2 -translate-y-1/2 h-6 w-6 text-slate-300">
                                <Lock />
                            </div>
                            <Input
                                type="password"
                                placeholder="登录密码"
                                className="h-20 pl-16 pr-8 text-xl rounded-[24px] bg-slate-50 border-none shadow-sm focus-visible:ring-1 focus-visible:ring-primary/20 transition-all font-bold placeholder:text-slate-200 placeholder:font-medium"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                disabled={isLoading}
                                autoComplete="current-password"
                            />
                        </div>
                    </div>

                    <Button
                        type="submit"
                        disabled={isLoading}
                        className="w-full h-20 rounded-[28px] text-2xl font-black shadow-xl shadow-primary/20 active:scale-95 transition-all text-white"
                    >
                        {isLoading ? (
                            <div className="flex items-center gap-3">
                                <div className="w-6 h-6 border-4 border-white/20 border-t-white rounded-full animate-spin" />
                                <span>验证身份...</span>
                            </div>
                        ) : "立即开启"}
                    </Button>
                </form>
            </div>

            {/* Bottom Footer */}
            <div className="mt-auto py-8 text-center animate-in slide-in-from-bottom-8 duration-1000 delay-500">
                <p className="text-slate-200 font-black text-[10px] tracking-[0.4em] uppercase">
                    Secure Entry Pism Project 2026
                </p>
            </div>
        </div>
    );
}
