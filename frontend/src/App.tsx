import {
  RouterProvider,
  createBrowserRouter,
} from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import ErrorPage from "./pages/ErrorPage";
import ProtectedRoute from "./components/common/ProtectedRoute";
import MainPage from "./pages/MainPage";
import ReportPage from "./pages/ReportPage";
import StoragePage from "./pages/StoragePage";
import BadgePage from "./pages/BadgePage";
import DesignPage from "./pages/DesignPage";
import AuthenticatedRoute from "./components/common/AuthenticatedRoute";
import TestPage from "./pages/MainPage";
import AuthCallback from "./pages/AuthCallbackPage";
import ReceiptPage from "./pages/ReceiptPage";
import {
  QueryClient,
  QueryClientProvider,
} from "@tanstack/react-query";
import RecipePage from "./pages/RecipePage";

const queryClient = new QueryClient();

function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <ProtectedRoute />,
      // errorElement: <ErrorPage />,
      children: [
        {
          path: "/",
          element: <MainPage />,
        },
        {
          path: "/report",
          element: <ReportPage />,
        },
        {
          path: "/storage/:storageName",
          element: <StoragePage />,
        },
        {
          path: "/badge",
          element: <BadgePage />,
        },
        {
          path: "/design",
          element: <DesignPage />,
        },
        {
          path: "/test",
          element: <TestPage />,
        },
        {
          path: "/receipt",
          element: <ReceiptPage />,
        },
        {
          path: "/recipe",
          element: <RecipePage />,
        },
      ],
    },
    {
      path: "/",
      element: <AuthenticatedRoute />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: "/landing",
          element: <LandingPage />,
        },
        {
          path: "/auth",
          element: <AuthCallback />,
        },
      ],
    },
  ]);
  return (
  <QueryClientProvider client={queryClient}>
    <RouterProvider router={router} />
  </QueryClientProvider>
  );
}

export default App;
