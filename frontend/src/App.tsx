import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import LandingPage from './pages/LandingPage';
import ErrorPage from './pages/ErrorPage';
import ProtectedRoute from './components/common/ProtectedRoute';
import MainPage from './pages/MainPage';
import ReportPage from './pages/ReportPage';
import StoragePage from './pages/StoragePage';
import BadgePage from './pages/BadgePage';
import DesignPage from './pages/DesignPage';
import AuthenticatedRoute from './components/common/AuthenticatedRoute';
import TestPage from './pages/TestPage';

function App() {
  const router = createBrowserRouter([
    {
      path: '/',
      element: <ProtectedRoute />,
      // errorElement: <ErrorPage />,
      children: [
        {
          path: '/',
          element: <MainPage />,
        },
        {
          path: '/report',
          element: <ReportPage />,
        },
        {
          path: '/storage/:storageName',
          element: <StoragePage />,
        },
        {
          path: '/badge',
          element: <BadgePage />,
        },
        {
          path: '/design',
          element: <DesignPage />,
        },
        {
          path: '/test',
          element: <TestPage />,
        },
      ],
    },
    {
      path: '/',
      element: <AuthenticatedRoute />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: '/landing',
          element: <LandingPage />,
        },
      ]
    }
  ]);
  return <RouterProvider router={router} />;
}

export default App;
