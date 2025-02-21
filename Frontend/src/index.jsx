import { Router, Route } from '@solidjs/router';
import { render } from "solid-js/web";
import Layout from './Layout';
import "./index.css";

import ItemsPage from './pages/Items/ItemsPage';
import RolesPage from './pages/Roles/RolesPage';
import UsersPage from './pages/Users/UsersPage';
import Home from './pages/Home/HomePage';
import LoginPage from './pages/Login/LoginPage';

export default function App() {
  return (
    <Router>
        <Route component={Layout}>
          <Route path="/" component={Home} />
          <Route path="/items" component={ItemsPage} />
          <Route path="/roles" component={RolesPage} />
          <Route path="/users" component={UsersPage} />
        </Route>

      <Route path="/login" component={LoginPage} />
    </Router>
  );
}

render(() => <App />, document.getElementById("root"));