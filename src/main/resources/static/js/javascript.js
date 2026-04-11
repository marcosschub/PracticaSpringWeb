// ─── STATE ───────────────────────────────────────────────────────────────────
let currentSection = 'products';
let editingId = null;
let allProducts = [];
let allUsers = [];
let allSales = [];

const BASE = () => document.getElementById('base-url').value.replace(/\/$/, '');

// ─── NAVIGATION ──────────────────────────────────────────────────────────────
function switchSection(section) {
  currentSection = section;
  document.querySelectorAll('.nav-item').forEach(el => {
    el.classList.toggle('active', el.dataset.section === section);
  });

  const config = {
    products: { title: 'Productos', sub: 'GET · POST · PUT · DELETE → api/products', table: 'Lista de Productos' },
    users:    { title: 'Usuarios',  sub: 'GET · POST · PUT · DELETE → api/users',    table: 'Lista de Usuarios' },
    sales:    { title: 'Ventas',    sub: 'GET · POST · PUT · DELETE → api/sales',     table: 'Lista de Ventas' },
  };
  document.getElementById('section-title').textContent = config[section].title;
  document.getElementById('section-sub').textContent   = config[section].sub;
  document.getElementById('table-title').textContent   = config[section].table;

  loadSection(section);
}

function loadCurrent() { loadSection(currentSection); }

// ─── API HELPERS ──────────────────────────────────────────────────────────────
async function apiFetch(path, options = {}) {
  const url = BASE() + path;
  const res = await fetch(url, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });
  return res;
}

// ─── LOAD ─────────────────────────────────────────────────────────────────────
async function loadSection(section) {
  setLoading();
  try {
    if (section === 'products') await loadProducts();
    else if (section === 'users') await loadUsers();
    else if (section === 'sales') await loadSales();
  } catch (e) {
    showError('No se pudo conectar con la API. Verificá la URL base.');
    document.getElementById('table-container').innerHTML = `<div class="empty"><div class="icon">⚠️</div>${e.message}</div>`;
  }
}

async function loadProducts() {
  const res = await apiFetch('/api/products');
  if (!res.ok) throw new Error('Error ' + res.status);
  allProducts = await res.json();
  document.getElementById('stat-products').textContent = allProducts.length;
  renderProducts(allProducts);
}

async function loadUsers() {
  const res = await apiFetch('/api/users');
  if (!res.ok) throw new Error('Error ' + res.status);
  allUsers = await res.json();
  document.getElementById('stat-users').textContent = allUsers.length;
  renderUsers(allUsers);
}

async function loadSales() {
  const res = await apiFetch('/api/sales');
  if (!res.ok) throw new Error('Error ' + res.status);
  allSales = await res.json();
  document.getElementById('stat-sales').textContent = allSales.length;
  renderSales(allSales);
}

// fetch products+users for dropdowns without re-rendering
async function fetchProductsQuiet() {
  try { const r = await apiFetch('/api/products'); allProducts = r.ok ? await r.json() : []; } catch(_){}
}
async function fetchUsersQuiet() {
  try { const r = await apiFetch('/api/users'); allUsers = r.ok ? await r.json() : []; } catch(_){}
}

// ─── STATS INIT ──────────────────────────────────────────────────────────────
async function initStats() {
  try { const r = await apiFetch('/api/products'); if(r.ok){ allProducts = await r.json(); document.getElementById('stat-products').textContent = allProducts.length; } } catch(_){}
  try { const r = await apiFetch('/api/users');    if(r.ok){ allUsers = await r.json();    document.getElementById('stat-users').textContent = allUsers.length; } } catch(_){}
  try { const r = await apiFetch('/api/sales');    if(r.ok){ allSales = await r.json();    document.getElementById('stat-sales').textContent = allSales.length; } } catch(_){}
}

// ─── RENDER PRODUCTS ─────────────────────────────────────────────────────────
function renderProducts(products) {
  if (!products.length) {
    document.getElementById('table-container').innerHTML = `<div class="empty"><div class="icon">📦</div>Sin productos. Creá el primero.</div>`;
    return;
  }
  document.getElementById('table-container').innerHTML = `
    <div class="table-wrap">
      <table>
        <thead><tr>
          <th>ID</th><th>Nombre</th><th>Precio</th><th>Descripción</th><th>Acciones</th>
        </tr></thead>
        <tbody>
          ${products.map(p => `
            <tr>
              <td>${p.id}</td>
              <td>${esc(p.name)}</td>
              <td><span class="badge badge-price">$${Number(p.price).toFixed(2)}</span></td>
              <td>${esc(p.description || '—')}</td>
              <td><div class="table-actions">
                <button class="btn btn-ghost btn-sm" onclick="openEditProduct(${p.id})">✏️ Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteProduct(${p.id})">🗑️ Eliminar</button>
              </div></td>
            </tr>`).join('')}
        </tbody>
      </table>
    </div>`;
}

// ─── RENDER USERS ─────────────────────────────────────────────────────────────
function renderUsers(users) {
  if (!users.length) {
    document.getElementById('table-container').innerHTML = `<div class="empty"><div class="icon">👤</div>Sin usuarios.</div>`;
    return;
  }
  document.getElementById('table-container').innerHTML = `
    <div class="table-wrap">
      <table>
        <thead><tr>
          <th>ID</th><th>Username</th><th>Email</th><th>Acciones</th>
        </tr></thead>
        <tbody>
          ${users.map(u => `
            <tr>
              <td>${u.id}</td>
              <td>${esc(u.username)}</td>
              <td>${esc(u.email)}</td>
              <td><div class="table-actions">
                <button class="btn btn-ghost btn-sm" onclick="openEditUser(${u.id})">✏️ Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteUser(${u.id})">🗑️ Eliminar</button>
              </div></td>
            </tr>`).join('')}
        </tbody>
      </table>
    </div>`;
}

// ─── RENDER SALES ─────────────────────────────────────────────────────────────
function renderSales(sales) {
  if (!sales.length) {
    document.getElementById('table-container').innerHTML = `<div class="empty"><div class="icon">🛒</div>Sin ventas.</div>`;
    return;
  }
  document.getElementById('table-container').innerHTML = `
    <div class="table-wrap">
      <table>
        <thead><tr>
          <th>ID</th><th>Producto</th><th>Cliente</th><th>Cantidad</th><th>Fecha</th><th>Acciones</th>
        </tr></thead>
        <tbody>
          ${sales.map(s => `
            <tr>
              <td>${s.id}</td>
              <td>${s.products ? esc(s.products.name) : '—'}</td>
              <td>${s.client ? esc(s.client.username) : '—'}</td>
              <td><span class="badge badge-qty">× ${s.quantity}</span></td>
              <td>${s.saleDate || '—'}</td>
              <td><div class="table-actions">
                <button class="btn btn-ghost btn-sm" onclick="openEditSale(${s.id})">✏️ Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteSale(${s.id})">🗑️ Eliminar</button>
              </div></td>
            </tr>`).join('')}
        </tbody>
      </table>
    </div>`;
}

// ─── MODAL OPEN ───────────────────────────────────────────────────────────────
function openCreateModal() {
  editingId = null;
  if (currentSection === 'products') openProductModal(null);
  else if (currentSection === 'users') openUserModal(null);
  else if (currentSection === 'sales') openSaleModal(null);
}

function openProductModal(product) {
  document.getElementById('modal-title').innerHTML = product
    ? 'Editar <span style="color:var(--accent)">Producto</span>'
    : 'Nuevo <span style="color:var(--accent)">Producto</span>';
  document.getElementById('modal-body').innerHTML = `
    <div class="form-group"><label>ID</label>
      <input type="number" id="f-id" value="${product ? product.id : ''}" placeholder="1" ${product ? 'readonly' : ''}>
    </div>
    <div class="form-group"><label>Nombre</label>
      <input type="text" id="f-name" value="${product ? esc(product.name) : ''}" placeholder="Nombre del producto">
    </div>
    <div class="form-group"><label>Precio</label>
      <input type="number" step="0.01" id="f-price" value="${product ? product.price : ''}" placeholder="0.00">
    </div>
    <div class="form-group"><label>Descripción</label>
      <input type="text" id="f-desc" value="${product ? esc(product.description || '') : ''}" placeholder="Descripción opcional">
    </div>`;
  openModal();
}

function openUserModal(user) {
  document.getElementById('modal-title').innerHTML = user
    ? 'Editar <span style="color:var(--accent2)">Usuario</span>'
    : 'Nuevo <span style="color:var(--accent2)">Usuario</span>';
  document.getElementById('modal-body').innerHTML = `
    <div class="form-group"><label>ID</label>
      <input type="number" id="f-id" value="${user ? user.id : ''}" placeholder="1" ${user ? 'readonly' : ''}>
    </div>
    <div class="form-group"><label>Username</label>
      <input type="text" id="f-username" value="${user ? esc(user.username) : ''}" placeholder="usuario123">
    </div>
    <div class="form-group"><label>Email</label>
      <input type="email" id="f-email" value="${user ? esc(user.email) : ''}" placeholder="correo@ejemplo.com">
    </div>
    <div class="form-group"><label>Contraseña</label>
      <input type="password" id="f-password" placeholder="${user ? '(dejar vacío para no cambiar)' : 'contraseña'}">
    </div>`;
  openModal();
}

async function openSaleModal(sale) {
  await fetchProductsQuiet();
  await fetchUsersQuiet();
  document.getElementById('modal-title').innerHTML = sale
    ? 'Editar <span style="color:var(--accent3)">Venta</span>'
    : 'Nueva <span style="color:var(--accent3)">Venta</span>';
  document.getElementById('modal-body').innerHTML = `
    <div class="form-group"><label>ID Venta</label>
      <input type="number" id="f-id" placeholder="ID de la venta">
    </div>
    <div class="form-group"><label>Producto</label>
      <select id="f-product">
        <option value="">— Seleccioná un producto —</option>
        ${allProducts.map(p => `<option value="${p.id}">${p.id} · ${esc(p.name)}</option>`).join('')}
      </select>
    </div>
    <div class="form-group"><label>Cliente</label>
      <select id="f-client">
        <option value="">— Seleccioná un cliente —</option>
        ${allUsers.map(u => `<option value="${u.id}">${u.id} · ${esc(u.username)}</option>`).join('')}
      </select>
    </div>
    <div class="form-group"><label>Cantidad</label>
      <input type="number" id="f-qty" placeholder="1" min="1">
    </div>`;
  openModal();
}

function openEditProduct(id) {
  const p = allProducts.find(x => x.id === id);
  if (!p) return showError('Producto no encontrado localmente.');
  editingId = id;
  openProductModal(p);
}

function openEditUser(id) {
  const u = allUsers.find(x => x.id === id);
  if (!u) return showError('Usuario no encontrado localmente.');
  editingId = id;
  openUserModal(u);
}

function openEditSale(id) {
  const s = allSales.find(x => x.id === id);
  if (!s) return showError('Venta no encontrada localmente.');
  editingId = id;
  openSaleModal(s);
}

function openModal() {
  document.getElementById('modal').style.display = 'flex';
}
function closeModal() {
  document.getElementById('modal').style.display = 'none';
  editingId = null;
}
function closeModalOnOverlay(e) {
  if (e.target === document.getElementById('modal')) closeModal();
}

// ─── SUBMIT ───────────────────────────────────────────────────────────────────
async function submitModal() {
  if (currentSection === 'products') await submitProduct();
  else if (currentSection === 'users') await submitUser();
  else if (currentSection === 'sales') await submitSale();
}

async function submitProduct() {
  const id = parseInt(document.getElementById('f-id').value);
  const body = {
    id,
    name:        document.getElementById('f-name').value,
    price:       parseFloat(document.getElementById('f-price').value),
    description: document.getElementById('f-desc').value,
  };
  if (!body.name || isNaN(body.price)) return showError('Nombre y precio son requeridos.');

  try {
    if (editingId) {
      const res = await apiFetch(`/api/products/${editingId}`, { method: 'PUT', body: JSON.stringify(body) });
      handleResponse(res, 'Producto actualizado', 202);
    } else {
      const res = await apiFetch('/api/products', { method: 'POST', body: JSON.stringify(body) });
      handleResponse(res, 'Producto creado', 201);
    }
    closeModal();
    await loadProducts();
  } catch(e) { showError(e.message); }
}

async function submitUser() {
  const id = parseInt(document.getElementById('f-id').value);
  const pw = document.getElementById('f-password').value;
  const body = {
    id,
    username: document.getElementById('f-username').value,
    email:    document.getElementById('f-email').value,
    password: pw || (editingId ? undefined : ''),
  };
  if (!body.username || !body.email) return showError('Username y email son requeridos.');

  try {
    if (editingId) {
      const res = await apiFetch(`/api/users/${editingId}`, { method: 'PUT', body: JSON.stringify(body) });
      handleResponse(res, 'Usuario actualizado', 202);
    } else {
      const res = await apiFetch('/api/users', { method: 'POST', body: JSON.stringify(body) });
      handleResponse(res, 'Usuario creado', 201);
    }
    closeModal();
    await loadUsers();
  } catch(e) { showError(e.message); }
}

async function submitSale() {
  const idSale    = parseInt(document.getElementById('f-id').value);
  const idProduct = parseInt(document.getElementById('f-product').value);
  const idClient  = parseInt(document.getElementById('f-client').value);
  const quantity  = parseInt(document.getElementById('f-qty').value);

  if (!idProduct || !idClient || !quantity) return showError('Todos los campos son requeridos.');

  try {
    if (editingId) {
      const product = allProducts.find(p => p.id === idProduct);
      const client  = allUsers.find(u => u.id === idClient);
      const body = { id: editingId, products: product, client: client, quantity: quantity };
      const res = await apiFetch('/api/sales/' + editingId, { method: 'PUT', body: JSON.stringify(body) });
      handleResponse(res, 'Venta actualizada', 202);
    } else {
      if (!idSale) return showError('El ID de venta es requerido.');
      const body = { idSale, idProduct, idClient, quantity };
      const res = await apiFetch('/api/sales', { method: 'POST', body: JSON.stringify(body) });
      handleResponse(res, 'Venta registrada', 201);
    }
    closeModal();
    await loadSales();
  } catch(e) { showError(e.message); }
}

// ─── DELETE ───────────────────────────────────────────────────────────────────
async function deleteProduct(id) {
  if (!confirm(`¿Eliminar producto #${id}?`)) return;
  try {
    const res = await apiFetch(`/api/products/${id}`, { method: 'DELETE' });
    handleResponse(res, 'Producto eliminado', 204);
    await loadProducts();
  } catch(e) { showError(e.message); }
}

async function deleteUser(id) {
  if (!confirm(`¿Eliminar usuario #${id}?`)) return;
  try {
    const res = await apiFetch(`/api/users/${id}`, { method: 'DELETE' });
    handleResponse(res, 'Usuario eliminado', 204);
    await loadUsers();
  } catch(e) { showError(e.message); }
}

async function deleteSale(id) {
  if (!confirm(`¿Eliminar venta #${id}?`)) return;
  try {
    // SaleController.delete recibe SaleEntity en body
    const sale = allSales.find(s => s.id === id);
    const res = await apiFetch(`/api/sales/${id}`, { method: 'DELETE', body: JSON.stringify(sale || {id}) });
    handleResponse(res, 'Venta eliminada', 204);
    await loadSales();
  } catch(e) { showError(e.message); }
}

// ─── HELPERS ──────────────────────────────────────────────────────────────────
function handleResponse(res, successMsg, expectedCode) {
  if (res.status === expectedCode || res.ok) {
    showSuccess(successMsg);
    updateStats();
  } else if (res.status === 409) {
    showError('Elemento duplicado (ID ya existe).');
  } else if (res.status === 404) {
    showError('Elemento no encontrado.');
  } else {
    showError(`Error HTTP ${res.status}`);
  }
}

function updateStats() {
  document.getElementById('stat-products').textContent = allProducts.length;
  document.getElementById('stat-users').textContent    = allUsers.length;
  document.getElementById('stat-sales').textContent    = allSales.length;
}

function setLoading() {
  document.getElementById('table-container').innerHTML =
    `<div class="loading"><span class="spinner"></span> Cargando datos...</div>`;
}

function esc(str) {
  if (!str) return '';
  return String(str).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
}

function showToast(msg, type = 'info') {
  const el = document.createElement('div');
  el.className = `toast ${type}`;
  const icons = { success: '✓', error: '✕', info: 'ℹ' };
  el.innerHTML = `<span>${icons[type]}</span> ${msg}`;
  document.getElementById('toast-container').appendChild(el);
  setTimeout(() => el.remove(), 3500);
}
function showSuccess(msg) { showToast(msg, 'success'); }
function showError(msg)   { showToast(msg, 'error'); }

// ─── INIT ─────────────────────────────────────────────────────────────────────
window.addEventListener('DOMContentLoaded', () => {
  initStats();
  loadSection('products');
});