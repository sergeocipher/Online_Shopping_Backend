import { useEffect, useState, type FormEvent } from 'react'
import './App.css'
import { listBrands, createBrand } from './api/brands'
import { listCategories, createCategory } from './api/categories'
import { listStyles, createStyle } from './api/styles'
import { listVariants, createVariant } from './api/variants'
import { listStores, createStore } from './api/stores'
import { listInventories, createInventory } from './api/inventories'
import { listInventoryEvents, createInventoryEvent } from './api/inventoryEvents'
import { listRoles, createRole } from './api/roles'
import { listUsers, createUser } from './api/users'
import { login } from './api/auth'
import { getAuthToken, setAuthToken } from './api/client'
import type {
  AuthRequest,
  Brand,
  BrandCreatePayload,
  Category,
  CategoryCreatePayload,
  Inventory,
  InventoryCreatePayload,
  InventoryEvent,
  InventoryEventCreatePayload,
  Role,
  RoleCreatePayload,
  Store,
  StoreCreatePayload,
  Style,
  StyleCreatePayload,
  User,
  UserCreatePayload,
  Variant,
  VariantCreatePayload,
} from './types'

const sections = [
  { key: 'dashboard', label: 'Dashboard', hint: 'Overview' },
  { key: 'brands', label: 'Brands', hint: 'Manage brands' },
  { key: 'categories', label: 'Categories', hint: 'Manage categories' },
  { key: 'styles', label: 'Styles', hint: 'Style catalog' },
  { key: 'variants', label: 'Variants', hint: 'SKU details' },
  { key: 'stores', label: 'Stores', hint: 'Store list' },
  { key: 'inventories', label: 'Inventory', hint: 'Stock by store' },
  { key: 'inventoryEvents', label: 'Events', hint: 'Stock movements' },
  { key: 'roles', label: 'Roles', hint: 'Access control' },
  { key: 'users', label: 'Users', hint: 'Accounts' },
] as const

type SectionKey = (typeof sections)[number]['key']

function App() {
  const [activeSection, setActiveSection] = useState<SectionKey>('dashboard')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const [brands, setBrands] = useState<Brand[]>([])
  const [categories, setCategories] = useState<Category[]>([])
  const [styles, setStyles] = useState<Style[]>([])
  const [variants, setVariants] = useState<Variant[]>([])
  const [stores, setStores] = useState<Store[]>([])
  const [inventories, setInventories] = useState<Inventory[]>([])
  const [inventoryEvents, setInventoryEvents] = useState<InventoryEvent[]>([])
  const [roles, setRoles] = useState<Role[]>([])
  const [users, setUsers] = useState<User[]>([])

  const [brandForm, setBrandForm] = useState<BrandCreatePayload>({ name: '', description: '' })
  const [categoryForm, setCategoryForm] = useState<CategoryCreatePayload>({ name: '', description: '' })
  const [styleForm, setStyleForm] = useState<StyleCreatePayload>({
    name: '',
    season: '',
    imageUrl: '',
    isActive: true,
    isListedOnline: true,
    brandId: 0,
    categoryId: 0,
  })
  const [variantForm, setVariantForm] = useState<VariantCreatePayload>({
    sku: '',
    size: '',
    color: '',
    barcodeValue: '',
    qrCodeValue: '',
    isActive: true,
    styleId: 0,
  })
  const [storeForm, setStoreForm] = useState<StoreCreatePayload>({
    name: '',
    location: '',
    isActive: true,
    isPublic: true,
  })
  const [inventoryForm, setInventoryForm] = useState<InventoryCreatePayload>({
    quantity: 0,
    reorderLevel: 0,
    storeId: 0,
    variantId: 0,
  })
  const [inventoryEventForm, setInventoryEventForm] = useState<InventoryEventCreatePayload>({
    eventType: '',
    quantityChange: 0,
    reason: '',
    inventoryId: 0,
  })
  const [roleForm, setRoleForm] = useState<RoleCreatePayload>({ name: '', description: '' })
  const [userForm, setUserForm] = useState<UserCreatePayload>({
    username: '',
    password: '',
    isActive: true,
    roleId: 0,
    storeId: undefined,
  })

  const [loginForm, setLoginForm] = useState<AuthRequest>({ username: '', password: '' })
  const [authLoading, setAuthLoading] = useState(false)
  const [authError, setAuthError] = useState<string | null>(null)
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(!!getAuthToken())

  const loadSection = async (section: SectionKey) => {
    if (!isAuthenticated) return
    setLoading(true)
    setError(null)
    try {
      switch (section) {
        case 'brands':
          setBrands(await listBrands())
          break
        case 'categories':
          setCategories(await listCategories())
          break
        case 'styles':
          setStyles(await listStyles())
          setBrands(await listBrands())
          setCategories(await listCategories())
          break
        case 'variants':
          setVariants(await listVariants())
          setStyles(await listStyles())
          break
        case 'stores':
          setStores(await listStores())
          break
        case 'inventories':
          setInventories(await listInventories())
          setStores(await listStores())
          setVariants(await listVariants())
          break
        case 'inventoryEvents':
          setInventoryEvents(await listInventoryEvents())
          setInventories(await listInventories())
          break
        case 'roles':
          setRoles(await listRoles())
          break
        case 'users':
          setUsers(await listUsers())
          setRoles(await listRoles())
          setStores(await listStores())
          break
        case 'dashboard':
        default:
          await Promise.all([
            listBrands().then(setBrands),
            listCategories().then(setCategories),
            listStyles().then(setStyles),
            listVariants().then(setVariants),
            listStores().then(setStores),
            listInventories().then(setInventories),
            listInventoryEvents().then(setInventoryEvents),
            listRoles().then(setRoles),
            listUsers().then(setUsers),
          ])
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load data')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    if (isAuthenticated) loadSection(activeSection)
  }, [activeSection, isAuthenticated])

  const handleLogin = async (event: FormEvent) => {
    event.preventDefault()
    if (!loginForm.username.trim() || !loginForm.password.trim()) return
    setAuthLoading(true)
    setAuthError(null)
    try {
      const res = await login({
        username: loginForm.username.trim(),
        password: loginForm.password,
      })
      setAuthToken(res.accessToken)
      setIsAuthenticated(true)
    } catch (err) {
      setAuthError(err instanceof Error ? err.message : 'Login failed')
      setIsAuthenticated(false)
    } finally {
      setAuthLoading(false)
    }
  }

  const handleLogout = () => {
    setAuthToken(undefined)
    setIsAuthenticated(false)
    setBrands([])
    setCategories([])
    setStyles([])
    setVariants([])
    setStores([])
    setInventories([])
    setInventoryEvents([])
    setRoles([])
    setUsers([])
  }

  const formatValue = (value: unknown) => {
    if (typeof value === 'boolean') return value ? 'Yes' : 'No'
    if (value === null || value === undefined || value === '') return ''
    return String(value)
  }

  const renderTable = (items: any[], columns: { key: string; label: string }[]) => {
    if (loading) return <p className="muted">Loading</p>
    if (error) return <p className="error">{error}</p>
    if (!items || items.length === 0) return <p className="muted">No records</p>

    return (
      <div className="table">
        <div className="table-row head">
          {columns.map((col) => (
            <div key={col.key}>{col.label}</div>
          ))}
        </div>
        {items.map((item) => (
          <div className="table-row" key={(item as any).id}>
            {columns.map((col) => (
              <div key={col.key}>{formatValue((item as any)[col.key])}</div>
            ))}
          </div>
        ))}
      </div>
    )
  }

  const renderDashboard = () => (
    <div className="cards">
      <div className="card">
        <h3>Inventory Items</h3>
        <p className="stat">{inventories.length}</p>
      </div>
      <div className="card">
        <h3>Variants</h3>
        <p className="stat">{variants.length}</p>
      </div>
      <div className="card">
        <h3>Stores</h3>
        <p className="stat">{stores.length}</p>
      </div>
      <div className="card">
        <h3>Events</h3>
        <p className="stat">{inventoryEvents.length}</p>
      </div>
    </div>
  )

  const renderSection = () => {
    switch (activeSection) {
      case 'brands':
        return (
          <div className="panel">
            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault()
                if (!brandForm.name.trim()) return
                createBrand({ name: brandForm.name.trim(), description: brandForm.description?.trim() || undefined })
                  .then((created) => {
                    setBrands((prev) => [created, ...prev])
                    setBrandForm({ name: '', description: '' })
                  })
                  .catch((err) => setError(err instanceof Error ? err.message : 'Failed to create brand'))
              }}
            >
              <label>
                Name
                <input
                  value={brandForm.name}
                  onChange={(e) => setBrandForm((p) => ({ ...p, name: e.target.value }))}
                  required
                />
              </label>
              <label>
                Description
                <input
                  value={brandForm.description || ''}
                  onChange={(e) => setBrandForm((p) => ({ ...p, description: e.target.value }))}
                />
              </label>
              <button type="submit">Add Brand</button>
            </form>
            {renderTable(brands, [
              { key: 'id', label: 'ID' },
              { key: 'name', label: 'Name' },
              { key: 'description', label: 'Description' },
            ])}
          </div>
        )
      case 'categories':
        return (
          <div className="panel">
            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault()
                if (!categoryForm.name.trim()) return
                createCategory({ name: categoryForm.name.trim(), description: categoryForm.description?.trim() || undefined })
                  .then((created) => {
                    setCategories((prev) => [created, ...prev])
                    setCategoryForm({ name: '', description: '' })
                  })
                  .catch((err) => setError(err instanceof Error ? err.message : 'Failed to create category'))
              }}
            >
              <label>
                Name
                <input
                  value={categoryForm.name}
                  onChange={(e) => setCategoryForm((p) => ({ ...p, name: e.target.value }))}
                  required
                />
              </label>
              <label>
                Description
                <input
                  value={categoryForm.description || ''}
                  onChange={(e) => setCategoryForm((p) => ({ ...p, description: e.target.value }))}
                />
              </label>
              <button type="submit">Add Category</button>
            </form>
            {renderTable(categories, [
              { key: 'id', label: 'ID' },
              { key: 'name', label: 'Name' },
              { key: 'description', label: 'Description' },
            ])}
          </div>
        )
      case 'styles':
        return (
          <div className="panel">
            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault()
                if (!styleForm.name.trim()) return
                const brandId = Number(styleForm.brandId)
                const categoryId = Number(styleForm.categoryId)
                if (!brandId || !categoryId) {
                  setError('Select brand and category')
                  return
                }
                createStyle({
                  name: styleForm.name.trim(),
                  season: styleForm.season?.trim() || undefined,
                  imageUrl: styleForm.imageUrl?.trim() || undefined,
                  isActive: styleForm.isActive,
                  isListedOnline: styleForm.isListedOnline,
                  brandId,
                  categoryId,
                })
                  .then((created) => {
                    setStyles((prev) => [created, ...prev])
                    setStyleForm({
                      name: '',
                      season: '',
                      imageUrl: '',
                      isActive: true,
                      isListedOnline: true,
                      brandId: 0,
                      categoryId: 0,
                    })
                  })
                  .catch((err) => setError(err instanceof Error ? err.message : 'Failed to create style'))
              }}
            >
              <label>
                Name
                <input value={styleForm.name} onChange={(e) => setStyleForm((p) => ({ ...p, name: e.target.value }))} required />
              </label>
              <label>
                Season
                <input value={styleForm.season || ''} onChange={(e) => setStyleForm((p) => ({ ...p, season: e.target.value }))} />
              </label>
              <label>
                Brand
                <select value={styleForm.brandId || ''} onChange={(e) => setStyleForm((p) => ({ ...p, brandId: Number(e.target.value) }))} required>
                  <option value="">Select brand</option>
                  {brands.map((b) => (
                    <option key={b.id} value={b.id}>{b.name}</option>
                  ))}
                </select>
              </label>
              <label>
                Category
                <select value={styleForm.categoryId || ''} onChange={(e) => setStyleForm((p) => ({ ...p, categoryId: Number(e.target.value) }))} required>
                  <option value="">Select category</option>
                  {categories.map((c) => (
                    <option key={c.id} value={c.id}>{c.name}</option>
                  ))}
                </select>
              </label>
              <div className="row">
                <label className="checkbox">
                  <input type="checkbox" checked={styleForm.isActive} onChange={(e) => setStyleForm((p) => ({ ...p, isActive: e.target.checked }))} /> Active
                </label>
                <label className="checkbox">
                  <input type="checkbox" checked={styleForm.isListedOnline} onChange={(e) => setStyleForm((p) => ({ ...p, isListedOnline: e.target.checked }))} /> Listed online
                </label>
              </div>
              <button type="submit">Add Style</button>
            </form>
            {renderTable(styles, [
              { key: 'id', label: 'ID' },
              { key: 'name', label: 'Name' },
              { key: 'season', label: 'Season' },
              { key: 'isActive', label: 'Active' },
            ])}
          </div>
        )
      case 'variants':
        return (
          <div className="panel">
            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault()
                if (!variantForm.sku.trim()) return
                const styleId = Number(variantForm.styleId)
                if (!styleId) {
                  setError('Select a style first')
                  return
                }
                createVariant({
                  sku: variantForm.sku.trim(),
                  size: variantForm.size.trim(),
                  color: variantForm.color.trim(),
                  barcodeValue: variantForm.barcodeValue?.trim() || undefined,
                  qrCodeValue: variantForm.qrCodeValue?.trim() || undefined,
                  isActive: variantForm.isActive,
                  styleId,
                })
                  .then((created) => {
                    setVariants((prev) => [created, ...prev])
                    setVariantForm({
                      sku: '',
                      size: '',
                      color: '',
                      barcodeValue: '',
                      qrCodeValue: '',
                      isActive: true,
                      styleId: 0,
                    })
                  })
                  .catch((err) => setError(err instanceof Error ? err.message : 'Failed to create variant'))
              }}
            >
              <label>
                SKU
                <input value={variantForm.sku} onChange={(e) => setVariantForm((p) => ({ ...p, sku: e.target.value }))} required />
              </label>
              <label>
                Size
                <input value={variantForm.size} onChange={(e) => setVariantForm((p) => ({ ...p, size: e.target.value }))} />
              </label>
              <label>
                Color
                <input value={variantForm.color} onChange={(e) => setVariantForm((p) => ({ ...p, color: e.target.value }))} />
              </label>
              <label>
                Style
                <select value={variantForm.styleId || ''} onChange={(e) => setVariantForm((p) => ({ ...p, styleId: Number(e.target.value) }))} required>
                  <option value="">Select style</option>
                  {styles.map((s) => (
                    <option key={s.id} value={s.id}>{s.name}</option>
                  ))}
                </select>
              </label>
              <button type="submit">Add Variant</button>
            </form>
            {renderTable(variants, [
              { key: 'id', label: 'ID' },
              { key: 'sku', label: 'SKU' },
              { key: 'size', label: 'Size' },
              { key: 'color', label: 'Color' },
              { key: 'styleId', label: 'Style ID' },
            ])}
          </div>
        )
      case 'stores':
        return (
          <div className="panel">
            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault()
                if (!storeForm.name.trim()) return
                createStore({
                  name: storeForm.name.trim(),
                  location: storeForm.location?.trim() || undefined,
                  isActive: storeForm.isActive,
                  isPublic: storeForm.isPublic,
                })
                  .then((created) => {
                    setStores((prev) => [created, ...prev])
                    setStoreForm({ name: '', location: '', isActive: true, isPublic: true })
                  })
                  .catch((err) => setError(err instanceof Error ? err.message : 'Failed to create store'))
              }}
            >
              <label>
                Name
                <input value={storeForm.name} onChange={(e) => setStoreForm((p) => ({ ...p, name: e.target.value }))} required />
              </label>
              <label>
                Location
                <input value={storeForm.location || ''} onChange={(e) => setStoreForm((p) => ({ ...p, location: e.target.value }))} />
              </label>
              <div className="row">
                <label className="checkbox">
                  <input type="checkbox" checked={storeForm.isActive} onChange={(e) => setStoreForm((p) => ({ ...p, isActive: e.target.checked }))} /> Active
                </label>
                <label className="checkbox">
                  <input type="checkbox" checked={storeForm.isPublic} onChange={(e) => setStoreForm((p) => ({ ...p, isPublic: e.target.checked }))} /> Public
                </label>
              </div>
              <button type="submit">Add Store</button>
            </form>
            {renderTable(stores, [
              { key: 'id', label: 'ID' },
              { key: 'name', label: 'Name' },
              { key: 'location', label: 'Location' },
              { key: 'isActive', label: 'Active' },
            ])}
          </div>
        )
      case 'inventories':
        return (
          <div className="panel">
            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault()
                const storeId = Number(inventoryForm.storeId)
                const variantId = Number(inventoryForm.variantId)
                if (!storeId || !variantId) {
                  setError('Select store and variant')
                  return
                }
                createInventory({
                  quantity: Number(inventoryForm.quantity ?? 0),
                  reorderLevel: Number(inventoryForm.reorderLevel ?? 0),
                  storeId,
                  variantId,
                })
                  .then((created) => {
                    setInventories((prev) => [created, ...prev])
                    setInventoryForm({ quantity: 0, reorderLevel: 0, storeId: 0, variantId: 0 })
                  })
                  .catch((err) => setError(err instanceof Error ? err.message : 'Failed to create inventory'))
              }}
            >
              <label>
                Store
                <select value={inventoryForm.storeId || ''} onChange={(e) => setInventoryForm((p) => ({ ...p, storeId: Number(e.target.value) }))} required>
                  <option value="">Select store</option>
                  {stores.map((s) => (
                    <option key={s.id} value={s.id}>{s.name}</option>
                  ))}
                </select>
              </label>
              <label>
                Variant
                <select value={inventoryForm.variantId || ''} onChange={(e) => setInventoryForm((p) => ({ ...p, variantId: Number(e.target.value) }))} required>
                  <option value="">Select variant</option>
                  {variants.map((v) => (
                    <option key={v.id} value={v.id}>{v.sku}</option>
                  ))}
                </select>
              </label>
              <label>
                Quantity
                <input type="number" value={inventoryForm.quantity ?? 0} onChange={(e) => setInventoryForm((p) => ({ ...p, quantity: Number(e.target.value) }))} />
              </label>
              <label>
                Reorder level
                <input type="number" value={inventoryForm.reorderLevel ?? 0} onChange={(e) => setInventoryForm((p) => ({ ...p, reorderLevel: Number(e.target.value) }))} />
              </label>
              <button type="submit">Add Inventory</button>
            </form>
            {renderTable(inventories, [
              { key: 'id', label: 'ID' },
              { key: 'quantity', label: 'Qty' },
              { key: 'reorderLevel', label: 'Reorder' },
              { key: 'storeId', label: 'Store' },
              { key: 'variantId', label: 'Variant' },
            ])}
          </div>
        )
      case 'inventoryEvents':
        return (
          <div className="panel">
            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault()
                if (!inventoryEventForm.eventType.trim()) return
                const inventoryId = Number(inventoryEventForm.inventoryId)
                if (!inventoryId) {
                  setError('Select inventory record')
                  return
                }
                createInventoryEvent({
                  eventType: inventoryEventForm.eventType.trim(),
                  quantityChange: Number(inventoryEventForm.quantityChange ?? 0),
                  reason: inventoryEventForm.reason?.trim() || undefined,
                  inventoryId,
                })
                  .then((created) => {
                    setInventoryEvents((prev) => [created, ...prev])
                    setInventoryEventForm({ eventType: '', quantityChange: 0, reason: '', inventoryId: 0 })
                  })
                  .catch((err) => setError(err instanceof Error ? err.message : 'Failed to create event'))
              }}
            >
              <label>
                Inventory
                <select value={inventoryEventForm.inventoryId || ''} onChange={(e) => setInventoryEventForm((p) => ({ ...p, inventoryId: Number(e.target.value) }))} required>
                  <option value="">Select inventory</option>
                  {inventories.map((inv) => (
                    <option key={inv.id} value={inv.id}>{`Inv ${inv.id} (Store ${inv.storeId})`}</option>
                  ))}
                </select>
              </label>
              <label>
                Event type
                <input value={inventoryEventForm.eventType} onChange={(e) => setInventoryEventForm((p) => ({ ...p, eventType: e.target.value }))} required />
              </label>
              <label>
                Quantity change
                <input type="number" value={inventoryEventForm.quantityChange} onChange={(e) => setInventoryEventForm((p) => ({ ...p, quantityChange: Number(e.target.value) }))} />
              </label>
              <label>
                Reason
                <input value={inventoryEventForm.reason || ''} onChange={(e) => setInventoryEventForm((p) => ({ ...p, reason: e.target.value }))} />
              </label>
              <button type="submit">Add Event</button>
            </form>
            {renderTable(inventoryEvents, [
              { key: 'id', label: 'ID' },
              { key: 'eventType', label: 'Type' },
              { key: 'quantityChange', label: 'Qty' },
              { key: 'inventoryId', label: 'Inventory' },
              { key: 'createdAt', label: 'Created' },
            ])}
          </div>
        )
      case 'roles':
        return (
          <div className="panel">
            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault()
                if (!roleForm.name.trim()) return
                createRole({ name: roleForm.name.trim(), description: roleForm.description?.trim() || undefined })
                  .then((created) => {
                    setRoles((prev) => [created, ...prev])
                    setRoleForm({ name: '', description: '' })
                  })
                  .catch((err) => setError(err instanceof Error ? err.message : 'Failed to create role'))
              }}
            >
              <label>
                Name
                <input value={roleForm.name} onChange={(e) => setRoleForm((p) => ({ ...p, name: e.target.value }))} required />
              </label>
              <label>
                Description
                <input value={roleForm.description || ''} onChange={(e) => setRoleForm((p) => ({ ...p, description: e.target.value }))} />
              </label>
              <button type="submit">Add Role</button>
            </form>
            {renderTable(roles, [
              { key: 'id', label: 'ID' },
              { key: 'name', label: 'Name' },
              { key: 'description', label: 'Description' },
            ])}
          </div>
        )
      case 'users':
        return (
          <div className="panel">
            <form
              className="form"
              onSubmit={(e) => {
                e.preventDefault()
                if (!userForm.username.trim() || !userForm.password.trim()) return
                const roleId = Number(userForm.roleId)
                if (!roleId) {
                  setError('Assign a role')
                  return
                }
                createUser({
                  username: userForm.username.trim(),
                  password: userForm.password,
                  isActive: userForm.isActive,
                  roleId,
                  storeId: userForm.storeId ? Number(userForm.storeId) : undefined,
                })
                  .then((created) => {
                    setUsers((prev) => [created, ...prev])
                    setUserForm({ username: '', password: '', isActive: true, roleId: 0, storeId: undefined })
                  })
                  .catch((err) => setError(err instanceof Error ? err.message : 'Failed to create user'))
              }}
            >
              <label>
                Username
                <input value={userForm.username} onChange={(e) => setUserForm((p) => ({ ...p, username: e.target.value }))} required />
              </label>
              <label>
                Password
                <input type="password" value={userForm.password} onChange={(e) => setUserForm((p) => ({ ...p, password: e.target.value }))} required />
              </label>
              <label>
                Role
                <select value={userForm.roleId || ''} onChange={(e) => setUserForm((p) => ({ ...p, roleId: Number(e.target.value) }))} required>
                  <option value="">Select role</option>
                  {roles.map((r) => (
                    <option key={r.id} value={r.id}>{r.name}</option>
                  ))}
                </select>
              </label>
              <label>
                Store (optional)
                <select value={userForm.storeId || ''} onChange={(e) => setUserForm((p) => ({ ...p, storeId: e.target.value ? Number(e.target.value) : undefined }))}>
                  <option value="">None</option>
                  {stores.map((s) => (
                    <option key={s.id} value={s.id}>{s.name}</option>
                  ))}
                </select>
              </label>
              <div className="row">
                <label className="checkbox">
                  <input type="checkbox" checked={userForm.isActive} onChange={(e) => setUserForm((p) => ({ ...p, isActive: e.target.checked }))} /> Active
                </label>
              </div>
              <button type="submit">Add User</button>
            </form>
            {renderTable(users, [
              { key: 'id', label: 'ID' },
              { key: 'username', label: 'Username' },
              { key: 'isActive', label: 'Active' },
              { key: 'roleId', label: 'Role' },
              { key: 'storeId', label: 'Store' },
            ])}
          </div>
        )
      case 'dashboard':
      default:
        return renderDashboard()
    }
  }

  return (
    <div className="app">
      <header className="topbar">
        <div>
          <h1>Inventory Management</h1>
          <p className="muted">Admin console</p>
        </div>
        <div className="auth-panel">
          {isAuthenticated ? (
            <>
              <span className="pill success">Authenticated</span>
              <button className="ghost" type="button" onClick={handleLogout}>
                Logout
              </button>
            </>
          ) : (
            <form className="auth-form" onSubmit={handleLogin}>
              <input
                placeholder="Username"
                value={loginForm.username}
                onChange={(e) => setLoginForm((p) => ({ ...p, username: e.target.value }))}
              />
              <input
                placeholder="Password"
                type="password"
                value={loginForm.password}
                onChange={(e) => setLoginForm((p) => ({ ...p, password: e.target.value }))}
              />
              <button type="submit" disabled={authLoading}>
                {authLoading ? 'Signing in' : 'Sign in'}
              </button>
              {authError && <span className="error">{authError}</span>}
            </form>
          )}
        </div>
      </header>

      <div className="layout">
        <aside className="sidebar">
          {sections.map((section) => (
            <button
              key={section.key}
              className={`nav-item ${activeSection === section.key ? 'active' : ''}`}
              onClick={() => setActiveSection(section.key)}
              disabled={!isAuthenticated && section.key !== 'dashboard'}
            >
              <span>{section.label}</span>
              <small>{section.hint}</small>
            </button>
          ))}
        </aside>

        <main className="content">
          <div className="section-header">
            <h2>{sections.find((s) => s.key === activeSection)?.label}</h2>
            <p className="muted">{sections.find((s) => s.key === activeSection)?.hint}</p>
          </div>
          {!isAuthenticated && activeSection !== 'dashboard' ? (
            <div className="panel">
              <p>Please sign in to access this section.</p>
            </div>
          ) : (
            renderSection()
          )}
        </main>
      </div>
    </div>
  )
}

export default App
