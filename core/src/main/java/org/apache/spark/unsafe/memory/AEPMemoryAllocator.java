package org.apache.spark.unsafe.memory;

import org.apache.spark.unsafe.VMPlatform;
import org.apache.spark.unsafe.memory.MemoryAllocator;
import org.apache.spark.unsafe.memory.MemoryBlock;

public class AEPMemoryAllocator implements MemoryAllocator{

  @Override
  public MemoryBlock allocate(long size) throws OutOfMemoryError {
    long address = VMPlatform.allocateMemory(size);
    MemoryBlock memoryBlock = new MemoryBlock(null, address, size);

    return memoryBlock;
  }

  @Override
  public void free(MemoryBlock memoryBlock) {
    assert (memoryBlock.getBaseObject() == null) :
      "baseObject not null; are you trying to use the AEP-heap allocator to free on-heap memory?";
    VMPlatform.freeMemory(memoryBlock.getBaseOffset());
  }
}
