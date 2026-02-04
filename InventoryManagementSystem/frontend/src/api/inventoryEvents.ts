import { apiFetch } from './client'
import type { InventoryEvent, InventoryEventCreatePayload } from '../types'

export async function listInventoryEvents(): Promise<InventoryEvent[]> {
  return apiFetch<InventoryEvent[]>('/api/inventory-events')
}

export async function createInventoryEvent(payload: InventoryEventCreatePayload): Promise<InventoryEvent> {
  return apiFetch<InventoryEvent>('/api/inventory-events', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
