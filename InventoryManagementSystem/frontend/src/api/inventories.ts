import { apiFetch } from './client'
import type { Inventory, InventoryCreatePayload, InventoryUpdatePayload } from '../types'

export async function listInventories(): Promise<Inventory[]> {
  return apiFetch<Inventory[]>('/api/inventories')
}

export async function createInventory(payload: InventoryCreatePayload): Promise<Inventory> {
  return apiFetch<Inventory>('/api/inventories', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

export async function updateInventory(id: number, payload: InventoryUpdatePayload): Promise<Inventory> {
  return apiFetch<Inventory>(`/api/inventories/${id}`, {
    method: 'PUT',
    body: JSON.stringify(payload),
  })
}
