import { useState, useEffect } from "react";
import {
    LayoutDashboard,
    Users,
    Settings,
    LogOut,
    Menu,
    Bell,
    Search,
    ChevronRight
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { logout } from "@/lib/auth";

export default function Home() {
    const [isSidebarOpen, setIsSidebarOpen] = useState(true);
    const [user, setUser] = useState<{ account: string; email: string } | null>(null);

    useEffect(() => {
        const storedUser = localStorage.getItem("pism_user");
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }

        // Auto-hide sidebar on smaller iPad portrait if needed, but for now keep it responsive
        const handleResize = () => {
            if (window.innerWidth < 1024) {
                setIsSidebarOpen(false);
            } else {
                setIsSidebarOpen(true);
            }
        };

        handleResize();
        window.addEventListener("resize", handleResize);
        return () => window.removeEventListener("resize", handleResize);
    }, []);

    const menuItems = [
        { icon: LayoutDashboard, label: "Dashboard", active: true },
        { icon: Users, label: "User Management", active: false },
        { icon: Settings, label: "System Settings", active: false },
    ];

    return (
        <div className="min-h-screen bg-slate-50 flex overflow-hidden">
            {/* Sidebar - iPad style */}
            <aside
                className={`bg-white border-r border-slate-200 transition-all duration-300 ease-in-out z-20 ${isSidebarOpen ? "w-72" : "w-0 -translate-x-full lg:w-20 lg:translate-x-0"
                    } flex flex-col`}
            >
                <div className="h-20 flex items-center px-6 border-b border-slate-100">
                    <div className="bg-primary h-10 w-10 rounded-xl flex items-center justify-center text-white shadow-lg shadow-primary/20">
                        <span className="font-bold text-xl">P</span>
                    </div>
                    {isSidebarOpen && <span className="ml-3 font-bold text-xl text-slate-800 tracking-tight">Phoenix</span>}
                </div>

                <nav className="flex-1 py-8 px-4 space-y-2">
                    {menuItems.map((item, index) => (
                        <button
                            key={index}
                            className={`w-full flex items-center px-4 py-4 rounded-2xl transition-all duration-200 ${item.active
                                ? "bg-primary text-white shadow-md shadow-primary/20 lg:justify-center"
                                : "text-slate-500 hover:bg-slate-50 hover:text-slate-900"
                                } ${!isSidebarOpen && "lg:justify-center"}`}
                        >
                            <item.icon className={`h-6 w-6 ${item.active ? "" : "text-slate-400"}`} />
                            {isSidebarOpen && <span className="ml-4 font-medium text-lg">{item.label}</span>}
                        </button>
                    ))}
                </nav>

                <div className="p-4 border-t border-slate-100">
                    <Button
                        variant="ghost"
                        className={`w-full h-14 rounded-2xl text-slate-500 hover:text-red-600 hover:bg-red-50 flex items-center ${!isSidebarOpen && "lg:justify-center"
                            }`}
                        onClick={logout}
                    >
                        <LogOut className="h-6 w-6" />
                        {isSidebarOpen && <span className="ml-4 font-medium text-lg">Logout</span>}
                    </Button>
                </div>
            </aside>

            {/* Main Content Area */}
            <main className="flex-1 flex flex-col relative overflow-y-auto">
                {/* Header */}
                <header className="h-20 bg-white/80 backdrop-blur-md border-b border-slate-100 flex items-center justify-between px-8 sticky top-0 z-10">
                    <div className="flex items-center gap-4">
                        <button
                            onClick={() => setIsSidebarOpen(!isSidebarOpen)}
                            className="p-2 h-12 w-12 rounded-2xl bg-slate-50 text-slate-600 hover:bg-slate-100 transition-colors lg:hidden"
                        >
                            <Menu className="h-7 w-7" />
                        </button>
                        <div className="hidden md:flex relative">
                            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-slate-400" />
                            <input
                                type="text"
                                placeholder="Quick search..."
                                className="bg-slate-50 border-none rounded-2xl pl-10 pr-4 h-12 w-64 text-slate-600 focus:ring-2 focus:ring-primary/20 outline-none"
                            />
                        </div>
                    </div>

                    <div className="flex items-center gap-6">
                        <button className="relative p-2 h-12 w-12 rounded-2xl hover:bg-slate-50 text-slate-400 transition-colors">
                            <Bell className="h-6 w-6" />
                            <span className="absolute top-3 right-3 h-2.5 w-2.5 bg-red-500 rounded-full border-2 border-white"></span>
                        </button>
                        <div className="flex items-center gap-3 pl-2 border-l border-slate-100">
                            <div className="text-right hidden sm:block font-medium">
                                <div className="text-slate-900 leading-tight">{user?.account || "Admin"}</div>
                                <div className="text-slate-400 text-sm">{user?.email || "admin@pism.com"}</div>
                            </div>
                            <div className="h-12 w-12 rounded-2xl bg-primary/10 text-primary flex items-center justify-center font-bold text-xl ring-4 ring-slate-50 shadow-inner">
                                {user?.account?.charAt(0).toUpperCase() || "A"}
                            </div>
                        </div>
                    </div>
                </header>

                {/* Content */}
                <div className="p-10 space-y-10">
                    <div className="flex items-end justify-between">
                        <div className="space-y-2">
                            <h1 className="text-4xl font-extrabold text-slate-900 tracking-tight">Overview</h1>
                            <p className="text-slate-500 text-xl font-medium">Welcome back to Pism Phoenix Management.</p>
                        </div>
                        <Button size="lg" className="h-14 px-8 rounded-2xl shadow-lg shadow-primary/20 text-lg font-bold">
                            Generate Report
                        </Button>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                        <Card className="border-none shadow-xl shadow-slate-200/50 rounded-[32px] overflow-hidden group hover:scale-[1.02] transition-all duration-300">
                            <CardHeader className="bg-gradient-to-br from-primary to-blue-600 p-8">
                                <div className="bg-white/20 p-3 w-12 h-12 rounded-2xl backdrop-blur-sm mb-4">
                                    <LayoutDashboard className="text-white w-6 h-6" />
                                </div>
                                <CardTitle className="text-white text-2xl font-bold">System Status</CardTitle>
                                <CardDescription className="text-white/80 text-lg">Real-time health check</CardDescription>
                            </CardHeader>
                            <CardContent className="p-8">
                                <div className="flex items-center justify-between py-2">
                                    <span className="text-slate-600 text-lg font-medium">Authentication Service</span>
                                    <span className="flex items-center text-green-500 font-bold bg-green-50 px-3 py-1 rounded-full text-sm">
                                        <span className="h-2 w-2 bg-green-500 rounded-full mr-2 animate-pulse"></span>
                                        Online
                                    </span>
                                </div>
                                <div className="flex items-center justify-between py-2">
                                    <span className="text-slate-600 text-lg font-medium">Core API Layer</span>
                                    <span className="flex items-center text-green-500 font-bold bg-green-50 px-3 py-1 rounded-full text-sm">
                                        <span className="h-2 w-2 bg-green-500 rounded-full mr-2 animate-pulse"></span>
                                        Operational
                                    </span>
                                </div>
                            </CardContent>
                        </Card>

                        <Card className="border-none shadow-xl shadow-slate-200/50 rounded-[32px] p-8 hover:scale-[1.02] transition-all duration-300">
                            <div className="bg-orange-100 p-4 w-16 h-16 rounded-[24px] mb-6 flex items-center justify-center">
                                <Users className="text-orange-600 w-8 h-8" />
                            </div>
                            <h3 className="text-2xl font-black text-slate-800 mb-2">Total Users</h3>
                            <div className="text-5xl font-black text-slate-900 mb-6 tracking-tighter">1,284</div>
                            <div className="flex items-center text-green-500 font-bold text-lg">
                                <ChevronRight className="rotate-270 h-5 w-5 mr-1" />
                                +12.5% <span className="text-slate-400 font-medium ml-2">since last month</span>
                            </div>
                        </Card>

                        <Card className="border-none shadow-xl shadow-slate-200/50 rounded-[32px] p-8 hover:scale-[1.02] transition-all duration-300">
                            <div className="bg-purple-100 p-4 w-16 h-16 rounded-[24px] mb-6 flex items-center justify-center">
                                <Bell className="text-purple-600 w-8 h-8" />
                            </div>
                            <h3 className="text-2xl font-black text-slate-800 mb-2">Pending Alerts</h3>
                            <div className="text-5xl font-black text-slate-900 mb-6 tracking-tighter">7</div>
                            <Button variant="link" className="p-0 text-purple-600 font-bold text-lg h-auto">
                                View all notifications
                            </Button>
                        </Card>
                    </div>
                </div>
            </main>
        </div>
    );
}
