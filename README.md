# Nova Engineering - ECO AE Extension / 新星工程 - ECO AE 扩展

[![](https://github.com/skyjack2033/ExampleMod1.7.10/actions/workflows/build-and-test.yml/badge.svg)](https://github.com/skyjack2033/ExampleMod1.7.10/actions/workflows/build-and-test.yml)
[![](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

[English](#english) | [中文](#chinese)

---

## English

A **Minecraft 1.7.10 GTNH** port of [NovaEngineering-ECOAEExtension](https://github.com/sddsd2332/NovaEngineering-ECOAEExtension), an AE2 (Applied Energistics 2) addon mod that adds advanced multi-block machines for crafting, storage, and calculation.

Originally developed for Minecraft 1.12.2 with RetroFuturaGradle, ported to 1.7.10 GTNH Convention build system.

### Multi-block Machines

#### ECalculator — 外部化 AE2 合成 CPU
The ECalculator externalizes AE2's Crafting CPU into a multi-block structure, providing:
- **Thread Cores**: Handle crafting job scheduling (L4/L6/L9 tiers)
- **Hyper Thread Cores**: Parallel job processing
- **Parallel Processors**: Increase crafting parallelism
- **Cell Drives**: Store crafting storage cells
- **Transmitter Bus**: Connect to ME network
- **ME Channel**: Network integration point
- **Controller**: Central management block with GUI monitoring

Structure pattern: 3D multi-block with controller center, surrounded by functional blocks in a predefined pattern.

#### EFabricator — 多方块自动合成
The EFabricator is an advanced auto-crafting multi-block:
- **Workers**: Execute crafting recipes (L4/L6/L9 tiers)
- **Parallel Processors**: Recipe parallelism modifiers
- **Pattern Bus**: Store encoded patterns (230 slot capacity)
- **Pattern Search GUI**: Search and manage patterns
- **Vents**: Cooling system integration
- **ME Channel**: Pattern provider to ME network
- **Controller**: Central management with real-time status GUI

Features overclocking, active cooling, and coolant-based speedup.

#### EStorage — 扩展 AE2 存储
Extended storage multi-block with custom cell system:
- **Cell Drives**: Accept custom EStorage cells (L4/L6/L9)
- **Energy Cells**: Store and buffer ME power, with auto-balancing
- **Cell Types**: Item, Fluid, Gas cells with tiered capacity
- **Storage Buses**: Input/Output bus for external inventory access
- **Vents**: Thermal management
- **Controller**: Energy and storage monitoring GUI

Custom cells support blacklist filtering and have tiered byte capacities.

### Build Requirements

- **JDK 25+** (required by GTNH Convention 2.x)
- **Gradle 9.3.1** (auto-downloaded by wrapper)
- Network access to GTNH Maven repositories

### Getting Started

```bash
git clone https://github.com/skyjack2033/ExampleMod1.7.10.git
cd ExampleMod1.7.10
./gradlew build
./gradlew runClient
```

### Dependencies

| Dependency | Version |
|-----------|---------|
| AE2 Unofficial | rv3-beta-871-GTNH |
| GTNH Convention | 2.0.24 |
| UniMixins | via GTNH Convention |
| Lombok | 1.18.24 (compile-only) |

### License

This project is licensed under the **GNU General Public License v3.0**. See [LICENSE](LICENSE) for details.

### Credits

Original mod by **Kasumi_Nova**, **sddsd2332**, **WI_8614_ice**
1.7.10 port based on GTNH ExampleMod template by SinTh0r4s, TheElan, basdxz

---

## 中文

[English](#english) | [中文](#chinese)

**Nova Engineering - ECO AE Extension（新星工程 - ECO AE 扩展）** 是一个基于 Minecraft 1.7.10 GTNH 的 AE2（应用能源2）附属模组。从 1.12.2 原版移植至 1.7.10 GTNH 构建系统。

本模组引入了三个核心多方块结构机器，扩展了 AE2 的合成、存储与计算能力。

### 多方块结构介绍

#### ECalculator（电子计算器）
将 AE2 的合成 CPU 外部化为多方块结构：
- **线程核心（Thread Core）**：处理合成任务调度，分 L4/L6/L9 三个等级
- **超线程核心（Hyper Thread Core）**：支持并行任务处理
- **并行处理器（Parallel Processor）**：提升合成并行度
- **元件驱动器（Cell Drive）**：存放合成存储元件
- **发射器总线（Transmitter Bus）**：连接至 ME 网络
- **ME 通道（ME Channel）**：网络集成接口
- **控制器（Controller）**：中央管理方块，提供 GUI 监控面板

#### EFabricator（电子合成器）
高级自动合成多方块结构：
- **工作器（Worker）**：执行合成配方，分 L4/L6/L9 等级
- **并行处理器（Parallel Processor）**：配方并行度修改器
- **样板总线（Pattern Bus）**：存储编码样板（230 槽位）
- **样板搜索 GUI**：搜索和管理样板
- **通风口（Vent）**：冷却系统集成
- **ME 通道（ME Channel）**：向 ME 网络提供样板
- **控制器（Controller）**：中央管理，实时状态 GUI

支持超频（Overclocking）、主动冷却（Active Cooling）和基于冷却液的速度提升。

#### EStorage（电子存储）
扩展存储多方块结构，自定义元件系统：
- **元件驱动器（Cell Drive）**：接受自定义 EStorage 元件（L4/L6/L9）
- **能量元件（Energy Cell）**：存储和缓冲 ME 能量，自动均衡
- **元件类型**：物品、流体、气体元件，分级容量
- **存储总线（Storage Bus）**：输入/输出总线，连接外部容器
- **通风口（Vent）**：热量管理
- **控制器（Controller）**：能量和存储监控 GUI

自定义元件支持黑名单过滤，具有分级字节容量。

### 构建要求

- **JDK 25+**（GTNH Convention 2.x 需要）
- **Gradle 9.3.1**（通过 wrapper 自动下载）
- 可访问 GTNH Maven 仓库的网络

### 快速开始

```bash
git clone https://github.com/skyjack2033/ExampleMod1.7.10.git
cd ExampleMod1.7.10
./gradlew build
./gradlew runClient
```

### 项目结构

| 路径 | 说明 |
|------|------|
| `src/main/java/github/kasuminova/ecoaeextension/` | 主模组源码（从 1.12.2 移植） |
| `src/main/java/appeng/` | AE2 API 兼容存根 |
| `src/main/java/hellfirepvp/` | 模块化机械兼容层 |
| `src/main/java/github/kasuminova/mmce/` | MMCE 扩展兼容层 |
| `src/main/resources/assets/ecoaeextension/` | 模组资源（blockstates、模型、纹理、语言） |

### 移植状态

| 组件 | 状态 |
|------|------|
| 构建系统 | 完成 |
| AE2 API 存根 | 进行中 |
| MMCE 兼容层 | 完成 |
| Forge 兼容层 | 完成 |
| 主模组逻辑 | 进行中（迭代修复编译错误） |

### 许可证

本项目采用 **GNU 通用公共许可证 v3.0 (GPLv3)**。详见 [LICENSE](LICENSE)。

### 致谢

原版模组作者：**Kasumi_Nova**、**sddsd2332**、**WI_8614_ice**
1.7.10 移植基于 GTNH 示例模组模板（SinTh0r4s、TheElan、basdxz）
