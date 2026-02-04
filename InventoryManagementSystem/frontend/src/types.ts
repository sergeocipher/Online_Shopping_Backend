export type Brand = {
  id: number;
  name: string;
  description: string | null;
  createdAt: string;
};

export type BrandCreatePayload = {
  name: string;
  description?: string;
};

export type BrandUpdatePayload = BrandCreatePayload;

export type Category = {
  id: number;
  name: string;
  description: string | null;
  createdAt: string;
};

export type CategoryCreatePayload = {
  name: string;
  description?: string;
};

export type Style = {
  id: number;
  name: string;
  season: string | null;
  imageUrl: string | null;
  isActive: boolean;
  isListedOnline: boolean;
  brandId: number;
  categoryId: number;
  createdAt: string;
};

export type StyleCreatePayload = {
  name: string;
  season?: string;
  imageUrl?: string;
  isActive: boolean;
  isListedOnline: boolean;
  brandId: number;
  categoryId: number;
};

export type Variant = {
  id: number;
  sku: string;
  size: string | null;
  color: string | null;
  barcodeValue: string | null;
  qrCodeValue: string | null;
  isActive: boolean;
  styleId: number;
  createdAt: string;
};

export type VariantCreatePayload = {
  sku: string;
  size: string;
  color: string;
  barcodeValue?: string;
  qrCodeValue?: string;
  isActive: boolean;
  styleId: number;
};

export type Store = {
  id: number;
  name: string;
  location: string | null;
  isActive: boolean;
  isPublic: boolean;
  createdAt: string;
};

export type StoreCreatePayload = {
  name: string;
  location?: string;
  isActive: boolean;
  isPublic: boolean;
};

export type Inventory = {
  id: number;
  quantity: number;
  reorderLevel: number;
  storeId: number;
  variantId: number;
  updatedAt: string;
};

export type InventoryCreatePayload = {
  quantity?: number;
  reorderLevel?: number;
  storeId: number;
  variantId: number;
};

export type InventoryUpdatePayload = {
  quantity?: number;
  reorderLevel?: number;
  storeId: number;
  variantId: number;
};

export type InventoryEvent = {
  id: number;
  eventType: string;
  quantityChange: number;
  reason: string | null;
  inventoryId: number;
  createdAt: string;
};

export type InventoryEventCreatePayload = {
  eventType: string;
  quantityChange: number;
  reason?: string;
  inventoryId: number;
};

export type Role = {
  id: number;
  name: string;
  description: string | null;
  createdAt: string;
};

export type RoleCreatePayload = {
  name: string;
  description?: string;
};

export type User = {
  id: number;
  username: string;
  isActive: boolean;
  roleId: number;
  storeId: number | null;
  createdAt: string;
};

export type UserCreatePayload = {
  username: string;
  password: string;
  isActive: boolean;
  roleId: number;
  storeId?: number;
};

export type AuthRequest = {
  username: string;
  password: string;
};

export type AuthResponse = {
  accessToken: string;
  tokenType: string;
  expiresAt: string;
  username: string;
};
