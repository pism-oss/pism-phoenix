import { useState, useEffect } from "react";
import {
    Users,
    ShieldCheck,
    BookOpen,
    History,
    LogOut,
    LayoutGrid
} from "lucide-react";
import { Card, CardTitle, CardDescription } from "@/components/ui/card";
import { logout } from "@/lib/auth";
import { Button } from "@/components/ui/button";

interface Entry {
    id: string;
    title: string;
    description: string;
    icon: any;
    color: string;
    bg: string;
}

export default function Home() {
    const [user, setUser] = useState<{ account: string; email: string } | null>(null);
    const [activeEntryId, setActiveEntryId] = useState<string | null>(null);

    useEffect(() => {
        const storedUser = localStorage.getItem("pism_user");
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    const entries: Entry[] = [
        {
            id: "user-mgmt",
            title: "用户管理",
            description: "管理系统用户信息及状态",
            icon: Users,
            color: "text-blue-600",
            bg: "bg-blue-50"
        },
        {
            id: "perms",
            title: "权限配置",
            description: "设置系统角色及功能权限",
            icon: ShieldCheck,
            color: "text-green-600",
            bg: "bg-green-50"
        },
        {
            id: "dict",
            title: "数据字典",
            description: "维护系统通用数据及常量",
            icon: BookOpen,
            color: "text-orange-600",
            bg: "bg-orange-50"
        },
        {
            id: "logs",
            title: "日志审计",
            description: "查看系统运行及操作日志",
            icon: History,
            color: "text-purple-600",
            bg: "bg-purple-50"
        }
    ];

    const activeEntry = entries.find(e => e.id === activeEntryId);

    // Dashboard View Component
    const DashboardView = () => (
        <div className="p-10 space-y-10 w-full animate-in fade-in zoom-in-95 duration-500">
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-6 w-full">
                {/* User Info Card (First Card) */}
                <Card className="border-none shadow-md bg-white rounded-[32px] overflow-hidden flex flex-col justify-between p-8 min-h-[300px]">
                    <div className="space-y-6">
                        <div className="flex items-center gap-4">
                            <div className="h-16 w-16 rounded-3xl bg-primary/10 text-primary flex items-center justify-center font-bold text-3xl ring-4 ring-slate-50 shadow-inner">
                                {user?.account?.charAt(0).toUpperCase() || "A"}
                            </div>
                            <div>
                                <h3 className="text-2xl font-black text-slate-800 tracking-tight">{user?.account || "管理员"}</h3>
                                <p className="text-slate-400 font-medium">{user?.email || "admin@pism.com"}</p>
                            </div>
                        </div>
                        <div className="pt-2">
                            <div className="inline-flex items-center px-3 py-1 rounded-full bg-green-50 text-green-600 text-sm font-bold">
                                <span className="h-2 w-2 bg-green-500 rounded-full mr-2 animate-pulse"></span>
                                在线
                            </div>
                        </div>
                    </div>
                    <Button
                        variant="ghost"
                        className="w-full h-14 rounded-2xl text-slate-400 hover:text-red-600 hover:bg-red-50 text-lg font-bold mt-auto border border-dashed border-slate-200"
                        onClick={logout}
                    >
                        <LogOut className="h-6 w-6 mr-3" />
                        退出登录
                    </Button>
                </Card>

                {/* Functional Entries */}
                {entries.map((entry) => (
                    <Card
                        key={entry.id}
                        className="border-none shadow-md hover:shadow-2xl hover:scale-[1.02] transition-all duration-300 cursor-pointer group rounded-[32px] overflow-hidden flex flex-col p-8 bg-white"
                        onClick={() => setActiveEntryId(entry.id)}
                    >
                        <div className={`${entry.bg} ${entry.color} p-5 w-20 h-20 rounded-3xl mb-8 flex items-center justify-center transition-transform group-hover:rotate-6 duration-300 shadow-sm`}>
                            <entry.icon className="w-10 h-10" />
                        </div>
                        <CardTitle className="text-2xl font-black text-slate-800 tracking-tight mb-2">{entry.title}</CardTitle>
                        <CardDescription className="text-slate-400 text-lg font-medium leading-snug">{entry.description}</CardDescription>

                        <div className="mt-auto pt-8">
                            <div className="h-2 w-full bg-slate-100 rounded-full overflow-hidden">
                                <div className={`h-full w-0 group-hover:w-full transition-all duration-700 ease-out ${entry.bg.replace('bg-', 'bg-').replace('-50', '-200')} opacity-50`}></div>
                            </div>
                        </div>
                    </Card>
                ))}
            </div>
        </div>
    );

    // Tabbed View Component
    const TabbedView = () => (
        <div className="flex flex-col h-screen overflow-hidden animate-in fade-in slide-in-from-bottom-4 duration-500">
            {/* Tab Bar Replacement for Header */}
            <div className="h-20 bg-white border-b border-slate-200 flex items-center px-6 gap-2 sticky top-0 z-20 overflow-x-auto no-scrollbar shadow-sm">
                {/* User / Logout Tab (Fixed Left, Non-content) */}
                <div className="flex items-center bg-slate-50 p-2 pr-4 rounded-[20px] gap-3 mr-4 border border-slate-100">
                    <div className="h-10 w-10 rounded-xl bg-primary/10 text-primary flex items-center justify-center font-bold text-lg ring-2 ring-white">
                        {user?.account?.charAt(0).toUpperCase() || "A"}
                    </div>
                    <div className="hidden sm:block">
                        <div className="text-sm font-black text-slate-800 leading-tight">{user?.account || "Admin"}</div>
                    </div>
                    <button
                        onClick={logout}
                        className="ml-2 p-2 rounded-xl text-slate-400 hover:text-red-600 hover:bg-red-50 transition-colors"
                        title="退出登录"
                    >
                        <LogOut className="h-5 w-5" />
                    </button>
                </div>

                <div className="h-10 w-[1px] bg-slate-200 mx-2"></div>

                {/* Functional Tabs */}
                <div className="flex items-center gap-2">
                    {entries.map(entry => (
                        <button
                            key={entry.id}
                            onClick={() => setActiveEntryId(entry.id)}
                            className={`flex items-center px-6 py-3 rounded-[20px] transition-all duration-300 font-bold text-base whitespace-nowrap ${activeEntryId === entry.id
                                ? "bg-primary text-white shadow-lg shadow-primary/20 scale-105"
                                : "text-slate-500 hover:bg-slate-100"
                                }`}
                        >
                            <entry.icon className={`h-5 w-5 mr-3 ${activeEntryId === entry.id ? 'text-white' : entry.color}`} />
                            {entry.title}
                        </button>
                    ))}

                    <button
                        onClick={() => setActiveEntryId(null)}
                        className="ml-4 p-3 rounded-[20px] text-slate-400 hover:bg-slate-100 transition-all flex items-center gap-2"
                        title="返回仪表盘"
                    >
                        <LayoutGrid className="h-5 w-5" />
                        <span className="text-sm font-bold">首页</span>
                    </button>
                </div>
            </div>

            {/* Content Area */}
            <main className="flex-1 bg-slate-50 p-10 overflow-y-auto w-full max-w-7xl mx-auto">
                <div className="bg-white rounded-[40px] shadow-xl border border-slate-100 p-12 min-h-[calc(100vh-180px)] animate-in zoom-in-95 duration-700">
                    <div className="flex items-center gap-6 mb-10 border-b border-slate-50 pb-8">
                        <div className={`${activeEntry?.bg} ${activeEntry?.color} p-5 w-20 h-20 rounded-3xl flex items-center justify-center shadow-inner`}>
                            {activeEntry && <activeEntry.icon className="w-10 h-10" />}
                        </div>
                        <div>
                            <h2 className="text-4xl font-black text-slate-900 tracking-tight">{activeEntry?.title}</h2>
                            <p className="text-slate-400 text-xl font-medium mt-1">{activeEntry?.description}</p>
                        </div>
                    </div>

                    {/* Placeholder for actual functionality */}
                    <div className="w-full h-96 border-4 border-dashed border-slate-100 rounded-[32px] flex items-center justify-center">
                        <div className="text-center space-y-4">
                            <div className="p-6 bg-slate-50 rounded-full inline-block">
                                {activeEntry && <activeEntry.icon className="w-16 h-16 text-slate-200" />}
                            </div>
                            <p className="text-slate-300 text-2xl font-black">功能模块开发中...</p>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );

    return (
        <div className="min-h-screen bg-slate-50">
            {activeEntryId === null ? <DashboardView /> : <TabbedView />}
        </div>
    );
}
